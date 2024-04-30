package org.jxch.capital.client.service;

import org.jxch.capital.client.db.pg.dto.ParentTemplateConfigDto;

import java.util.List;

public interface ParentTemplateConfigService {
    List<ParentTemplateConfigDto> findByTemplateName(String templateName);

    ParentTemplateConfigDto findByConfigName(String configName);

    ParentTemplateConfigDto save(ParentTemplateConfigDto dto);

    void deleteById(Long id);

    void deleteByConfigName(String configName);
}
