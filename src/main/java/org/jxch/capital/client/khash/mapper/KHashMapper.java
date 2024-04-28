package org.jxch.capital.client.khash.mapper;

import org.jetbrains.annotations.NotNull;
import org.jxch.capital.client.khash.reader.CsvBaostockIntradayDto;
import org.jxch.capital.client.stock.dto.KLine;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface KHashMapper {

    @Mapping(source = "time", target = "date")
    KLine toKLineByCsvBaostockIntraday(CsvBaostockIntradayDto dto);

    default KLine toKLineByCsvBaostockIntraday(CsvBaostockIntradayDto dto, String code) {
        return toKLineByCsvBaostockIntraday(dto).setCode(code);
    }

    List<KLine> toKLineByCsvBaostockIntraday(List<CsvBaostockIntradayDto> dtoList);

    default List<KLine> toKLineByCsvBaostockIntraday(@NotNull List<CsvBaostockIntradayDto> dtoList, String code) {
        return dtoList.stream().map(dto -> toKLineByCsvBaostockIntraday(dto, code)).toList();
    }

}
