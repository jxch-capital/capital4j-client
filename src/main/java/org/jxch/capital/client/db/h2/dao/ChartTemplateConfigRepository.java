package org.jxch.capital.client.db.h2.dao;

import org.jxch.capital.client.db.h2.po.ChartTemplateConfig;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

@Deprecated
public interface ChartTemplateConfigRepository extends JpaRepository<ChartTemplateConfig, Long> {

    ChartTemplateConfig findByConfigName(String configName);

    List<ChartTemplateConfig> findAllByTemplateName(String templateName);

    void deleteByConfigName(String configName);

}
