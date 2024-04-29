package org.jxch.capital.client.service.impl;

import lombok.RequiredArgsConstructor;
import org.jxch.capital.client.db.h2.dao.ParentTemplateConfigRepository;
import org.jxch.capital.client.db.h2.dto.ParentTemplateConfigDto;
import org.jxch.capital.client.db.h2.mapper.ParentTemplateConfigMapper;
import org.jxch.capital.client.service.ParentTemplateConfigService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ParentTemplateConfigServiceImpl implements ParentTemplateConfigService {
    private final ParentTemplateConfigRepository repository;
    private final ParentTemplateConfigMapper mapper;

    @Override
    public List<ParentTemplateConfigDto> findByTemplateName(String templateName) {
        return mapper.toChartTemplateConfigDto(repository.findAllByTemplateName(templateName));
    }

    @Override
    public ParentTemplateConfigDto findByConfigName(String configName) {
        return mapper.toChartTemplateConfigDto(repository.findByConfigName(configName));
    }

    @Override
    public ParentTemplateConfigDto save(ParentTemplateConfigDto dto) {
        return mapper.toChartTemplateConfigDto(repository.saveAndFlush(mapper.toChartTemplateConfig(dto)));
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    @Override
    public void deleteByConfigName(String configName) {
        repository.deleteByConfigName(configName);
    }

}
