package org.jxch.capital.client.khash.ch;

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
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
@RequiredArgsConstructor
public class CsvBaostock5M2Hash2Clickhouse implements Files2Hash2DB {
    private final CsvBaostockKLineFileReader csvBaostockKLineFileReader;
    private final KHashCN5M5LService kHashCN5M5LService;
    private static final ThreadLocal<Integer> THREAD_LOCAL_RETRY = new ThreadLocal<>();

    @Override
    @Async(ThreadConfig.IO_THREAD_POOL)
    public void toHash2DB(@NotNull List<File> files) {
        KLines2KHash2Agg2KHashCN5M5Ls agg = new KLines2KHash2Agg2KHashCN5M5Ls();
        var proxy = SpringUtil.getBean(CsvBaostock5M2Hash2Clickhouse.class);
        files.forEach(file -> proxy.toHash2DB(file, agg));
    }

    @Async(ThreadConfig.IO_THREAD_POOL)
    public void toHash2DB(@NotNull File file, @NotNull KLines2KHash2Agg2KHashCN5M5Ls agg) {
        String[] nameSplit = file.getName().split("_");
        String fullCode = nameSplit[0];
        String[] codeSplit = fullCode.split("\\.");
        String ex = codeSplit[0];
        String code = codeSplit[1];
        List<KHashCN5M5LDto> dtoList = agg.agg(csvBaostockKLineFileReader.readeAll(file), code, ex);
        if (Objects.nonNull(dtoList) && !dtoList.isEmpty()) {
            kHashCN5M5LService.saveAll(dtoList);
        }
    }

    @Override
    @Async(ThreadConfig.IO_THREAD_POOL)
    public void toHash2DB(@NotNull List<File> files, String uuid) {
        KLines2KHash2Agg2KHashCN5M5Ls agg = new KLines2KHash2Agg2KHashCN5M5Ls();
        var proxy = SpringUtil.getBean(CsvBaostock5M2Hash2Clickhouse.class);
        files.forEach(file -> proxy.toHash2DB(file, agg, uuid));
    }

    @SneakyThrows
    @Async(ThreadConfig.IO_THREAD_POOL)
    public void toHash2DB(File file, KLines2KHash2Agg2KHashCN5M5Ls agg, String uuid) {
        try {
            toHash2DB(file, agg);
            SpringUtil.publishEvent(ProgressBarEvent.oneSucceedEvent(this, uuid));
        } catch (Throwable t) {
            SpringUtil.publishEvent(ProgressBarEvent.oneFailAndRetryEvent(this, uuid, t.getMessage()));
            log.error("{} 任务失败，一分钟后重试", uuid, t);
            TimeUnit.MINUTES.sleep(1);
            retryRecord();
            toHash2DB(file, agg, uuid);
        } finally {
            THREAD_LOCAL_RETRY.remove();
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
