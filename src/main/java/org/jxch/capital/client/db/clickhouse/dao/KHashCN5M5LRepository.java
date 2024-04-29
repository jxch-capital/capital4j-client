package org.jxch.capital.client.db.clickhouse.dao;


import org.apache.ibatis.annotations.Select;
import org.jxch.capital.client.db.clickhouse.po.KHashCN5M5L;

import java.math.BigDecimal;
import java.util.List;

public interface KHashCN5M5LRepository {

    void createTableIfNotExist();

    Integer insert(List<KHashCN5M5L> poList);

    @Select("SELECT * FROM k_hash_cn_5m_5l k WHERE k.hash5 BETWEEN #{from} and #{to}")
    List<KHashCN5M5L> findByHash5Between(Integer from, Integer to);

    @Select("SELECT * FROM k_hash_cn_5m_5l k WHERE k.hash10 BETWEEN #{from} and #{to}")
    List<KHashCN5M5L> findByHash10Between(Long from, Long to);

    @Select("SELECT * FROM k_hash_cn_5m_5l k WHERE k.hash16 BETWEEN #{from} and #{to}")
    List<KHashCN5M5L> findByHash16Between(Long from, Long to);

    @Select("SELECT * FROM k_hash_cn_5m_5l k WHERE k.hash24 BETWEEN #{from} and #{to}")
    List<KHashCN5M5L> findByHash24Between(BigDecimal from, BigDecimal to);

    @Select("SELECT * FROM k_hash_cn_5m_5l k WHERE k.hash48 BETWEEN #{from} and #{to}")
    List<KHashCN5M5L> findByHash48Between(BigDecimal from, BigDecimal to);

}
