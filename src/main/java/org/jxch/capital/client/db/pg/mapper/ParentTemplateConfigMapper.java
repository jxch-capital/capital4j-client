package org.jxch.capital.client.db.pg.mapper;

import org.jxch.capital.client.db.pg.dto.ParentTemplateConfigDto;
import org.jxch.capital.client.db.pg.po.ParentTemplateConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ParentTemplateConfigMapper {
    ParentTemplateConfigDto toChartTemplateConfigDto(ParentTemplateConfig po);

    List<ParentTemplateConfigDto> toChartTemplateConfigDto(List<ParentTemplateConfig> po);

    @Mapping(target = "createTime", ignore = true)
    @Mapping(target = "updateTime", ignore = true)
    ParentTemplateConfig toChartTemplateConfig(ParentTemplateConfigDto dto);

    List<ParentTemplateConfig> toChartTemplateConfig(List<ParentTemplateConfigDto> dto);
}
