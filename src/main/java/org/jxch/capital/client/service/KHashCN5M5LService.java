package org.jxch.capital.client.service;

import org.jxch.capital.client.db.clickhouse.dto.KHashCN5M5LDto;

import java.util.List;

public interface KHashCN5M5LService {

    Integer saveAll(List<KHashCN5M5LDto> dtoList);

    List<KHashCN5M5LDto> findByHash5Prefix(String hashPrefix);

    List<KHashCN5M5LDto> findByHash10Prefix(String hashPrefix);

    List<KHashCN5M5LDto> findByHash16Prefix(String hashPrefix);

    List<KHashCN5M5LDto> findByHash24Prefix(String hashPrefix);

    List<KHashCN5M5LDto> findByHash48Prefix(String hashPrefix);

}
