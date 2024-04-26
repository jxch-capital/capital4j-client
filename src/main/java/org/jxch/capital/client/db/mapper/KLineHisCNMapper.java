package org.jxch.capital.client.db.mapper;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ReflectUtil;
import lombok.NonNull;
import org.jxch.capital.client.db.dto.KLineHisCNDto;
import org.jxch.capital.client.db.sharding.k_line_his_cn.KLineHisCNSharding;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface KLineHisCNMapper {

    KLineHisCNSharding toKLineHisCN(KLineHisCNDto dto);

    List<KLineHisCNSharding> toKLineHisCN(List<KLineHisCNDto> dtoList);

    KLineHisCNDto toKLineHisCNDto(KLineHisCNSharding po);

    List<KLineHisCNDto> toKLineHisCNDto(List<KLineHisCNSharding> poList);

    default Object toEntity(KLineHisCNDto dto, Class<?> clazz) {
        Object obj = ReflectUtil.newInstance(clazz);
        BeanUtil.copyProperties(dto, obj);
        return obj;
    }

    default List<Object> toEntity(@NonNull List<KLineHisCNDto> dtoList, Class<?> clazz) {
        return dtoList.stream().map(dto -> toEntity(dto, clazz)).toList();
    }

}
