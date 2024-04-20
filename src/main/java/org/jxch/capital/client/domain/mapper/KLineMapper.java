package org.jxch.capital.client.domain.mapper;

import org.jxch.capital.client.python.dto.BSQueryKRes;
import org.jxch.capital.client.stock.KLine;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface KLineMapper {

    KLine toKLine(BSQueryKRes bsQueryKRes);

    List<KLine> toKLine(List<BSQueryKRes> bsQueryKRes);

}
