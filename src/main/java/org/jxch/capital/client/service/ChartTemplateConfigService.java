package org.jxch.capital.client.service;

import org.jxch.capital.client.db.dto.ChartTemplateConfigDto;

import java.util.List;

public interface ChartTemplateConfigService {

    List<ChartTemplateConfigDto> findByTemplateName(String templateName);

    ChartTemplateConfigDto findByConfigName(String configName);

    ChartTemplateConfigDto save(ChartTemplateConfigDto chartTemplateConfigDto);

    void deleteById(Long id);

    void deleteByConfigName(String configName);

}
