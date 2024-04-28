package org.jxch.capital.client.khash.rocks;

import cn.hutool.core.date.DateUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.alibaba.fastjson2.JSON;
import lombok.NonNull;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.jxch.capital.client.config.RocksDBConfig;
import org.jxch.capital.client.config.ThreadConfig;
import org.jxch.capital.client.khash.reader.CsvBaostockKLineFileReader;
import org.jxch.capital.client.stock.dto.KLine;
import org.rocksdb.RocksDB;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
public class CsvBaostockIntraday2RocksDB implements Files2RocksDB {
    private final CsvBaostockKLineFileReader csvBaostockKLineFileReader;
    private final RocksDB rocksDB;

    public CsvBaostockIntraday2RocksDB(CsvBaostockKLineFileReader csvBaostockKLineFileReader,
                                       @Qualifier(RocksDBConfig.KLINE_CN_5M_CODE_DATE_DB) RocksDB rocksDB) {
        this.csvBaostockKLineFileReader = csvBaostockKLineFileReader;
        this.rocksDB = rocksDB;
    }

    @Override
    public void toRocksDB(@NotNull List<File> files) {
        files.forEach(file -> SpringUtil.getBean(CsvBaostockIntraday2RocksDB.class).toRocksDB(file));
    }

    @Override
    @SneakyThrows
    public List<KLine> find(String code, Date date) {
        return JSON.parseArray(new String(rocksDB.get(getHashKey(date, code).getBytes()), StandardCharsets.UTF_8), KLine.class);
    }

    @Async(ThreadConfig.VIRTUAL_THREAD_POOL)
    public void toRocksDB(File file) {
        csvBaostockKLineFileReader.readeAll(file).stream().collect(Collectors.groupingBy(kLine -> getHashKey(kLine.getDate(), kLine.getCode()))).forEach(this::put);
    }

    @SneakyThrows
    public void put(@NotNull String key, @NotNull List<KLine> kLines) {
        rocksDB.put(key.getBytes(), JSON.toJSONString(kLines.stream().map(kLine -> kLine.setCode(null)).sorted(Comparator.comparing(KLine::getDate)).toList()).getBytes());
    }

    public String getHashKey(Date date, @NonNull String code) {
        String[] codes = code.trim().split("\\.");
        return DateUtil.format(date, "yyyyMMdd") + codes[codes.length - 1];
    }

}
