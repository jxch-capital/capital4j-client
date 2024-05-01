package org.jxch.capital.client.khash.hash2db;

import cn.hutool.core.exceptions.ExceptionUtil;
import cn.hutool.extra.spring.SpringUtil;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.jxch.capital.client.config.ThreadConfig;
import org.jxch.capital.client.db.pg.dto.KHashCN5M5LDto;
import org.jxch.capital.client.event.ProgressBarEvent;
import org.jxch.capital.client.khash.agg.KLines2KHash2Agg;
import org.jxch.capital.client.khash.agg.KLines2KHash2AggKHashCN5M5L;
import org.jxch.capital.client.khash.reader.CsvBaostockKLineFileReader;
import org.jxch.capital.client.service.KHashCN5M5LService;
import org.jxch.capital.client.stock.dto.KLine;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.sql.SQLTransientConnectionException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@Service
@RequiredArgsConstructor
public class CsvBaostock5M2Hash2DB implements Files2Hash2DB {
    private final static ThreadLocal<Long> RETRY_COUNTER = new ThreadLocal<>();
    private final CsvBaostockKLineFileReader csvBaostockKLineFileReader;
    private final KHashCN5M5LService kHashCN5M5LService;
    private final static Map<String, LinkedBlockingDeque<KHashCN5M5LDto>> JOB_QUEUE = new ConcurrentHashMap<>();
    private final static Map<String, AtomicInteger> JOB_COUNTER = new ConcurrentHashMap<>();

    @Override
    @Async(ThreadConfig.IO_THREAD_POOL)
    public void toHash2DB(@NotNull List<File> files) {
        toHash2DB(files, UUID.randomUUID().toString());
    }

    @Override
    @Async(ThreadConfig.IO_THREAD_POOL)
    public void toHash2DB(@NotNull List<File> files, String uuid) {
        var agg = new KLines2KHash2AggKHashCN5M5L();
        JOB_QUEUE.putIfAbsent(uuid, new LinkedBlockingDeque<>());
        JOB_COUNTER.putIfAbsent(uuid, new AtomicInteger(files.size()));
        files.forEach(file -> SpringUtil.getBean(CsvBaostock5M2Hash2DB.class).toHash2DB(file, agg, uuid));
    }

    @SneakyThrows
    private synchronized void saveAll(String uuid) {
        List<KHashCN5M5LDto> dtoList = new ArrayList<>(JOB_QUEUE.get(uuid).size());
        JOB_QUEUE.get(uuid).drainTo(dtoList);
        try {
            kHashCN5M5LService.saveAll(dtoList);
        } catch (Exception e) {
            JOB_QUEUE.get(uuid).addAll(dtoList);
            if (ExceptionUtil.getRootCause(e) instanceof SQLTransientConnectionException) {
                retryRecord();
                log.warn("链接数据库失败，{}分钟后重试 [{}]", RETRY_COUNTER.get() * 3, uuid);
                TimeUnit.MINUTES.sleep(RETRY_COUNTER.get() * 3);
            } else {
                throw e;
            }
        } finally {
            RETRY_COUNTER.remove();
            if (JOB_COUNTER.get(uuid).get() == 0 && !JOB_QUEUE.get(uuid).isEmpty()) {
                saveAll(uuid);
            }
            if (JOB_COUNTER.get(uuid).get() == 0) {
                JOB_COUNTER.remove(uuid);
                JOB_QUEUE.remove(uuid);
            }
        }
    }

    @Transactional
    @Async(ThreadConfig.IO_THREAD_POOL)
    public void do2Hash2DB(@NotNull File file, @NotNull KLines2KHash2Agg<KHashCN5M5LDto> agg, String uuid) {
        String[] nameSplit = file.getName().split("_");
        String fullCode = nameSplit[0];
        String[] codeSplit = fullCode.split("\\.");
        String ex = codeSplit[0];
        String code = codeSplit[1];
        List<KLine> kLines = csvBaostockKLineFileReader.readeAll(file);
        JOB_QUEUE.get(uuid).addAll(agg.agg(kLines, code, ex));
        if (JOB_COUNTER.get(uuid).decrementAndGet() % 20 == 0 || JOB_COUNTER.get(uuid).get() == 0) {
            saveAll(uuid);
        }
    }

    @Transactional
    @Async(ThreadConfig.IO_THREAD_POOL)
    public void toHash2DB(@NotNull File file, @NotNull KLines2KHash2Agg<KHashCN5M5LDto> agg, String uuid) {
        try {
            do2Hash2DB(file, agg, uuid);
            SpringUtil.publishEvent(ProgressBarEvent.oneSucceedEvent(this, uuid).setInfo(file.getName() + " succeed."));
        } catch (Throwable t) {
            log.error("{} 处理失败：[{}]", file.getName(), ExceptionUtil.getRootCauseMessage(t), t);
            SpringUtil.publishEvent(ProgressBarEvent.oneFailEvent(this, uuid, ExceptionUtil.getRootCauseMessage(t)).setInfo(file.getName() + " failed."));
        }
    }

    private void retryRecord() {
        if (Objects.nonNull(RETRY_COUNTER.get())) {
            RETRY_COUNTER.set(RETRY_COUNTER.get() + 1);
        } else {
            RETRY_COUNTER.set(1L);
        }
    }

    @Override
    public String getName() {
        return "CsvBaostock5M2Hash2DB";
    }

}
