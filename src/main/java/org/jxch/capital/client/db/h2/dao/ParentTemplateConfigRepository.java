package org.jxch.capital.client.db.h2.dao;

import org.jxch.capital.client.db.h2.po.ParentTemplateConfig;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ParentTemplateConfigRepository extends JpaRepository<ParentTemplateConfig, Long> {

    ParentTemplateConfig findByConfigName(String configName);

    List<ParentTemplateConfig> findAllByTemplateName(String templateName);

    void deleteByConfigName(String configName);

}
