package org.jxch.capital.client.db.mapper;

import org.jxch.capital.client.db.dto.KLineHisCNDto;
import org.jxch.capital.client.db.po.KLineHisCN;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface KLineHisCNMapper {

    KLineHisCN toKLineHisCN(KLineHisCNDto dto);

    List<KLineHisCN> toKLineHisCN(List<KLineHisCNDto> dtoList);

    KLineHisCNDto toKLineHisCNDto(KLineHisCN po);

    List<KLineHisCNDto> toKLineHisCNDto(List<KLineHisCN> poList);

}
