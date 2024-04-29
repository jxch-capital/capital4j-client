package org.jxch.capital.client.db.clickhouse.mapper;

import org.jxch.capital.client.db.clickhouse.dto.KHashCN5M5LDto;
import org.jxch.capital.client.db.clickhouse.po.KHashCN5M5L;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface KHashCN5M5LMapper {

    KHashCN5M5LDto toKHashCN5M5LDto(KHashCN5M5L kHashCN5M5L);

    List<KHashCN5M5LDto> toKHashCN5M5LDto(List<KHashCN5M5L> kHashCN5M5L);

    KHashCN5M5L toKHashCN5M5L(KHashCN5M5LDto kHashCN5M5LDto);

    List<KHashCN5M5L> toKHashCN5M5L(List<KHashCN5M5LDto> kHashCN5M5L);

}
