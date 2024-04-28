package org.jxch.capital.client.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jxch.capital.client.db.h2.dao.ChartTemplateConfigRepository;
import org.jxch.capital.client.db.h2.dto.ChartTemplateConfigDto;
import org.jxch.capital.client.db.h2.mapper.ChartTemplateConfigMapper;
import org.jxch.capital.client.service.ChartTemplateConfigService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Deprecated
@RequiredArgsConstructor
public class ChartTemplateConfigServiceImpl implements ChartTemplateConfigService {
    private final ChartTemplateConfigRepository chartTemplateConfigRepository;
    private final ChartTemplateConfigMapper chartTemplateConfigMapper;

    @Override
    public List<ChartTemplateConfigDto> findByTemplateName(String templateName) {
        return chartTemplateConfigMapper.toChartTemplateConfigDto(chartTemplateConfigRepository.findAllByTemplateName(templateName));
    }

    @Override
    public ChartTemplateConfigDto findByConfigName(String configName) {
        return chartTemplateConfigMapper.toChartTemplateConfigDto(chartTemplateConfigRepository.findByConfigName(configName));
    }

    @Override
    public ChartTemplateConfigDto save(ChartTemplateConfigDto chartTemplateConfigDto) {
        return chartTemplateConfigMapper.toChartTemplateConfigDto(
                chartTemplateConfigRepository.saveAndFlush(chartTemplateConfigMapper.toChartTemplateConfig(chartTemplateConfigDto))
        );
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        chartTemplateConfigRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void deleteByConfigName(String configName) {
        chartTemplateConfigRepository.deleteByConfigName(configName);
    }

}
