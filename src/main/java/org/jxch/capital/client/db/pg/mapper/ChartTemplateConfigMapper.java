package org.jxch.capital.client.db.pg.mapper;

import org.jxch.capital.client.db.pg.dto.ChartTemplateConfigDto;
import org.jxch.capital.client.db.pg.po.ChartTemplateConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Deprecated
@Mapper(componentModel = "spring")
public interface ChartTemplateConfigMapper {

    ChartTemplateConfigDto toChartTemplateConfigDto(ChartTemplateConfig po);

    List<ChartTemplateConfigDto> toChartTemplateConfigDto(List<ChartTemplateConfig> po);

    @Mapping(target = "createTime", ignore = true)
    @Mapping(target = "updateTime", ignore = true)
    ChartTemplateConfig toChartTemplateConfig(ChartTemplateConfigDto dto);

    List<ChartTemplateConfig> toChartTemplateConfig(List<ChartTemplateConfigDto> dto);

}