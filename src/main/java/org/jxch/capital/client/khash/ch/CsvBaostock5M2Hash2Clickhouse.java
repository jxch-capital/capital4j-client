package org.jxch.capital.client.khash.ch;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.TimeInterval;
import cn.hutool.core.exceptions.ExceptionUtil;
import cn.hutool.extra.spring.SpringUtil;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.jxch.capital.client.config.ThreadConfig;
import org.jxch.capital.client.db.clickhouse.dto.KHashCN5M5LDto;
import org.jxch.capital.client.event.ProgressBarEvent;
import org.jxch.capital.client.khash.agg.KLines2KHash2Agg2KHashCN5M5Ls;
import org.jxch.capital.client.khash.reader.CsvBaostockKLineFileReader;
import org.jxch.capital.client.service.KHashCN5M5LService;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.LongAdder;

@Slf4j
@Component
@RequiredArgsConstructor
public class CsvBaostock5M2Hash2Clickhouse implements Files2Hash2DB {
    private final CsvBaostockKLineFileReader csvBaostockKLineFileReader;
    private final KHashCN5M5LService kHashCN5M5LService;
    private static final ThreadLocal<Integer> THREAD_LOCAL_RETRY = new ThreadLocal<>();
    private final Map<String, AtomicInteger> jobCount = new ConcurrentHashMap<>();
    private final Map<String, LongAdder> jobSavedNumber = new ConcurrentHashMap<>();
    private final Map<String, LinkedBlockingDeque<KHashCN5M5LDto>> jobQueueMap = new ConcurrentHashMap<>();
    private static final Integer THRESHOLD = 5_000;

    @Override
    @Async(ThreadConfig.COMPUTE_THREAD_POOL)
    public void toHash2DB(@NotNull List<File> files) {
        KLines2KHash2Agg2KHashCN5M5Ls agg = new KLines2KHash2Agg2KHashCN5M5Ls();
        var proxy = SpringUtil.getBean(CsvBaostock5M2Hash2Clickhouse.class);
        String uuid = UUID.randomUUID().toString();
        files.forEach(file -> proxy.toHash2DBByUUID(file, agg, uuid));
    }

    @SneakyThrows
    private synchronized void saveAll(List<KHashCN5M5LDto> saving, String uuid) {
        try {
            TimeInterval timer = DateUtil.timer();
            log.info("saving -> {}", saving.size());
            kHashCN5M5LService.saveAll(saving);
            jobSavedNumber.get(uuid).add(saving.size());
            log.info("saved [{}] -> [{}/{}]", timer.intervalPretty(), saving.size(), jobSavedNumber.get(uuid).sum());
            TimeUnit.SECONDS.sleep(10);
        } catch (Throwable e) {
            retryRecord();
            if (THREAD_LOCAL_RETRY.get() > 10) {
                log.error("{}任务失败，请手动重试", uuid, e);
            }
            log.error("{}任务失败，{}分钟后重试 ->[{}]", uuid, THREAD_LOCAL_RETRY.get(), ExceptionUtil.getRootCauseMessage(e));
            TimeUnit.MINUTES.sleep(THREAD_LOCAL_RETRY.get());
            saveAll(saving, uuid);
        }
    }

    @Async(ThreadConfig.COMPUTE_THREAD_POOL)
    public void toHash2DBByUUID(@NotNull File file, @NotNull KLines2KHash2Agg2KHashCN5M5Ls agg, String uuid) {
        String[] nameSplit = file.getName().split("_");
        String fullCode = nameSplit[0];
        String[] codeSplit = fullCode.split("\\.");
        String ex = codeSplit[0];
        String code = codeSplit[1];
        List<KHashCN5M5LDto> dtoList = agg.agg(csvBaostockKLineFileReader.readeAll(file), code, ex);
        if (Objects.nonNull(dtoList) && !dtoList.isEmpty()) {
            LinkedBlockingDeque<KHashCN5M5LDto> jobDeque = jobQueueMap.get(uuid);
            jobDeque.addAll(dtoList);
            if (jobDeque.size() >= THRESHOLD) {
                List<KHashCN5M5LDto> saving = new ArrayList<>(jobDeque.size());
                jobDeque.drainTo(saving);
                if (saving.size() < THRESHOLD) {
                    jobDeque.addAll(saving);
                } else {
                    saveAll(saving, uuid);
                }
            }
        }

        jobCount.get(uuid).decrementAndGet();
        if (jobCount.get(uuid).get() == 0) {
            synchronized (jobCount.get(uuid)) {
                LinkedBlockingDeque<KHashCN5M5LDto> jobDeque = jobQueueMap.get(uuid);
                if (!jobDeque.isEmpty()) {
                    List<KHashCN5M5LDto> saving = new ArrayList<>(jobDeque.size());
                    jobDeque.drainTo(saving);
                    saveAll(saving, uuid);
                    jobQueueMap.remove(uuid);
                    jobCount.remove(uuid);
                    jobSavedNumber.remove(uuid);
                }
            }
        }
    }

    @Override
    @Async(ThreadConfig.COMPUTE_THREAD_POOL)
    public void toHash2DB(@NotNull List<File> files, String uuid) {
        jobCount.putIfAbsent(uuid, new AtomicInteger(files.size()));
        jobQueueMap.putIfAbsent(uuid, new LinkedBlockingDeque<>());
        jobSavedNumber.putIfAbsent(uuid, new LongAdder());
        KLines2KHash2Agg2KHashCN5M5Ls agg = new KLines2KHash2Agg2KHashCN5M5Ls();
        var proxy = SpringUtil.getBean(CsvBaostock5M2Hash2Clickhouse.class);
        files.forEach(file -> proxy.toHash2DB(file, agg, uuid));
    }

    @Async(ThreadConfig.COMPUTE_THREAD_POOL)
    public void toHash2DB(File file, KLines2KHash2Agg2KHashCN5M5Ls agg, String uuid) {
        try {
            toHash2DBByUUID(file, agg, uuid);
            SpringUtil.publishEvent(ProgressBarEvent.oneSucceedEvent(this, uuid));
        } catch (Throwable t) {
            SpringUtil.publishEvent(ProgressBarEvent.oneFailAndRetryEvent(this, uuid, t.getMessage()));
        }
    }

    private void retryRecord() {
        if (Objects.nonNull(THREAD_LOCAL_RETRY.get())) {
            THREAD_LOCAL_RETRY.set(THREAD_LOCAL_RETRY.get() + 1);
        } else {
            THREAD_LOCAL_RETRY.set(1);
        }
    }

    @Override
    public String getName() {
        return "CsvBaostock5M2Hash2Clickhouse";
    }

}
