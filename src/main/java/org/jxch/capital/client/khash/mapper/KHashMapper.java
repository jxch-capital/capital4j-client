package org.jxch.capital.client.khash.mapper;

import org.jxch.capital.client.khash.reader.CsvBaostockIntradayDto;
import org.jxch.capital.client.stock.dto.KLine;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface KHashMapper {

    @Mapping(source = "time", target = "date")
    KLine toKLineByCsvBaostockIntraday(CsvBaostockIntradayDto dto);

    List<KLine> toKLineByCsvBaostockIntraday(List<CsvBaostockIntradayDto> dtoList);

}
