package org.jxch.capital.client.db.clickhouse.dao;


import cn.hutool.extra.spring.SpringUtil;
import lombok.SneakyThrows;
import org.apache.ibatis.annotations.Select;
import org.jxch.capital.client.config.ThreadConfig;
import org.jxch.capital.client.db.clickhouse.po.KHashCN5M5L;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.*;

public interface KHashCN5M5LRepository {

    void createHash5TableIfNotExist();

    void createHash10TableIfNotExist();

    void createHash16TableIfNotExist();

    void createHash24TableIfNotExist();

    void createHash48TableIfNotExist();

    @SneakyThrows
    default void createTableIfNotExist() {
        CompletionService<Integer> completionService = new ExecutorCompletionService<>(SpringUtil.getBean(ThreadConfig.IO_THREAD_POOL, ExecutorService.class));
        completionService.submit(() -> {
            this.createHash5TableIfNotExist();
            return null;
        });
        completionService.submit(() -> {
            this.createHash10TableIfNotExist();
            return null;
        });
        completionService.submit(() -> {
            this.createHash16TableIfNotExist();
            return null;
        });
        completionService.submit(() -> {
            this.createHash24TableIfNotExist();
            return null;
        });
        completionService.submit(() -> {
            this.createHash48TableIfNotExist();
            return null;
        });

        for (int i = 0; i < 5; i++) {
            completionService.take().get();
        }
    }

    Integer insertHash5(List<KHashCN5M5L> poList);

    Integer insertHash10(List<KHashCN5M5L> poList);

    Integer insertHash16(List<KHashCN5M5L> poList);

    Integer insertHash24(List<KHashCN5M5L> poList);

    Integer insertHash48(List<KHashCN5M5L> poList);

    @SneakyThrows
    default void insert(List<KHashCN5M5L> poList) {
        CompletionService<Integer> completionService = new ExecutorCompletionService<>(SpringUtil.getBean(ThreadConfig.IO_THREAD_POOL, ExecutorService.class));
        completionService.submit(() -> insertHash5(poList));
        completionService.submit(() -> insertHash10(poList));
        completionService.submit(() -> insertHash16(poList));
        completionService.submit(() -> insertHash24(poList));
        completionService.submit(() -> insertHash48(poList));

        for (int i = 0; i < 5; i++) {
            completionService.take().get();
        }
    }

    @Select("SELECT * FROM k_hash_cn_5m_hash5 k WHERE k.hash5 BETWEEN #{from} and #{to}")
    List<KHashCN5M5L> findByHash5Between(Integer from, Integer to);

    @Select("SELECT * FROM k_hash_cn_5m_hash10 k WHERE k.hash10 BETWEEN #{from} and #{to}")
    List<KHashCN5M5L> findByHash10Between(Long from, Long to);

    @Select("SELECT * FROM k_hash_cn_5m_hash16 k WHERE k.hash16 BETWEEN #{from} and #{to}")
    List<KHashCN5M5L> findByHash16Between(Long from, Long to);

    @Select("SELECT * FROM k_hash_cn_5m_hash24 k WHERE k.hash24 BETWEEN #{from} and #{to}")
    List<KHashCN5M5L> findByHash24Between(BigDecimal from, BigDecimal to);

    @Select("SELECT * FROM k_hash_cn_5m_hash48 k WHERE k.hash48 BETWEEN #{from} and #{to}")
    List<KHashCN5M5L> findByHash48Between(BigDecimal from, BigDecimal to);

}
