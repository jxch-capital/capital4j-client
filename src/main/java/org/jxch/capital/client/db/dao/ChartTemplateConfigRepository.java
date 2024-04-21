package org.jxch.capital.client.db.dao;

import org.jxch.capital.client.db.po.ChartTemplateConfig;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChartTemplateConfigRepository extends JpaRepository<ChartTemplateConfig, Long> {

    ChartTemplateConfig findByConfigName(String configName);

    List<ChartTemplateConfig> findAllByTemplateName(String templateName);

    void deleteByConfigName(String configName);

}
