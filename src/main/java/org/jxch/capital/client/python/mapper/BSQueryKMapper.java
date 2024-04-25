package org.jxch.capital.client.python.mapper;

import cn.hutool.core.bean.BeanUtil;
import lombok.NonNull;
import org.jxch.capital.client.python.dto.BSQueryKParam;
import org.jxch.capital.client.python.dto.BSQueryKRes;
import org.jxch.capital.client.stock.dto.KLine;
import org.jxch.capital.client.stock.dto.StockQueryParam;
import org.jxch.capital.client.uilt.FileU;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.UUID;

@Mapper(componentModel = "spring")
public interface BSQueryKMapper {

    @Mapping(target = "date", expression = "java(bsQueryKRes.getDateTime())")
    KLine toKLine(BSQueryKRes bsQueryKRes);

    List<KLine> toKLine(List<BSQueryKRes> bsQueryKRes);

    default BSQueryKParam toBSQueryKParam(@NonNull StockQueryParam param) {
        BSQueryKParam bsQueryKParam = new BSQueryKParam();
        BeanUtil.copyProperties(param, bsQueryKParam);
        switch (param.getFrequency()) {
            case DAY_1 -> bsQueryKParam.setFrequency("d").setDayFields();
            case WEEK_1 -> bsQueryKParam.setFrequency("w").setDayFields();
            case MONTH_1 -> bsQueryKParam.setFrequency("m").setDayFields();
            case MINUTE_5 -> bsQueryKParam.setFrequency("5").setDailyFields();
            case MINUTE_15 -> bsQueryKParam.setFrequency("15").setDailyFields();
            case MINUTE_30 -> bsQueryKParam.setFrequency("30").setDailyFields();
            case MINUTE_60 -> bsQueryKParam.setFrequency("60").setDailyFields();
            default -> throw new IllegalArgumentException("Invalid frequency: " + param.getFrequency());
        }
        return bsQueryKParam.setCsvFile(FileU.tmpFilePath(UUID.randomUUID() + ".csv"));
    }


}
