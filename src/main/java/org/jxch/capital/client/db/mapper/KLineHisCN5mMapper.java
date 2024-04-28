package org.jxch.capital.client.db.mapper;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ReflectUtil;
import lombok.NonNull;
import org.jxch.capital.client.db.dto.KLineHisCN5mDto;
import org.jxch.capital.client.db.sharding.k_line_his_cn_5m.KLineHisCN5mSharding;
import org.jxch.capital.client.fx.dto.CsvCN5mKLineDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface KLineHisCN5mMapper {

    KLineHisCN5mSharding toKLineHisCN(KLineHisCN5mDto dto);

    List<KLineHisCN5mSharding> toKLineHisCN(List<KLineHisCN5mDto> dtoList);

    KLineHisCN5mDto toKLineHisCNDto(KLineHisCN5mSharding po);

    List<KLineHisCN5mDto> toKLineHisCNDto(List<KLineHisCN5mSharding> poList);

    default Object toEntity(KLineHisCN5mDto dto, Class<?> clazz) {
        Object obj = ReflectUtil.newInstance(clazz);
        BeanUtil.copyProperties(dto, obj);
        return obj;
    }

    default List<Object> toEntity(@NonNull List<KLineHisCN5mDto> dtoList, Class<?> clazz) {
        return dtoList.stream().map(dto -> toEntity(dto, clazz)).toList();
    }

    @Mapping(source = "time", target = "date")
    KLineHisCN5mDto toKLineHisCN5mDtoByCsvCN5mKLineDto(CsvCN5mKLineDto dto);

    List<KLineHisCN5mDto> toKLineHisCN5mDtoByCsvCN5mKLineDto(List<CsvCN5mKLineDto> dtoList);

}
