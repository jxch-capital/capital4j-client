package org.jxch.capital.client.db.pg.mapper;

import org.jxch.capital.client.db.pg.dto.KHashCN5M5LDto;
import org.jxch.capital.client.db.pg.sharding.po.KHashCN5M5L;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", builder = @Builder(disableBuilder = true))
public interface KHashCN5M5LMapper {

    @Mapping(target = "id", expression = "java(dto.encodAndGetId())")
    KHashCN5M5L toKHashCN5M5L(KHashCN5M5LDto dto);

    List<KHashCN5M5L> toKHashCN5M5L(List<KHashCN5M5LDto> dtoList);

    @Mapping(target = "date", expression = "java(kHashCN5M5LDto.decodeAndGetDate())")
    @Mapping(target = "codeNumberString", expression = "java(kHashCN5M5LDto.decodeAndGetCodeNumberString())")
    KHashCN5M5LDto toKHashCN5M5LDto(KHashCN5M5L po);

    List<KHashCN5M5LDto> toKHashCN5M5LDto(List<KHashCN5M5L> poList);

}
