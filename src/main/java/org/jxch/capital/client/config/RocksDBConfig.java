package org.jxch.capital.client.config;

import cn.hutool.core.io.FileUtil;
import cn.hutool.extra.spring.SpringUtil;
import jakarta.annotation.PreDestroy;
import lombok.Data;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.rocksdb.Options;
import org.rocksdb.RocksDB;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.File;
import java.nio.file.Path;
import java.util.Objects;

@Slf4j
@Data
@Configuration
@ConfigurationProperties(prefix = "app.rocksdb")
public class RocksDBConfig {
    public static final String KLINE_CN_5M_CODE_DATE_DB = "KLINE_CN_5M_CODE_DATE_DB";
    private String path;

    private void createRocksDirIfMissing() {
        FileUtil.mkdir(new File(path));
    }

    @SneakyThrows
    @Bean(name = KLINE_CN_5M_CODE_DATE_DB)
    public RocksDB rocksDBByKlineCn5mCodeDate() {
        createRocksDirIfMissing();
        RocksDB.loadLibrary();
        try (Options options = new Options().setCreateIfMissing(true)) {
            return RocksDB.open(options, Path.of(path).resolve(KLINE_CN_5M_CODE_DATE_DB).toString());
        }
    }

    @PreDestroy
    public void close() {
        RocksDB rocksDB = SpringUtil.getBean(RocksDB.class);
        if (Objects.nonNull(rocksDB)) {
            rocksDB.close();
        }
    }

}
