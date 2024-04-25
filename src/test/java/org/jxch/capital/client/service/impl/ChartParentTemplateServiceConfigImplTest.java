package org.jxch.capital.client.service.impl;

import com.alibaba.fastjson2.JSON;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.jxch.capital.client.db.dto.ChartTemplateConfigDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@Slf4j
@SpringBootTest
@ActiveProfiles("spring-boot-test")
class ChartParentTemplateServiceConfigImplTest {
    @Autowired
    private ChartTemplateConfigServiceImpl chartTemplateConfigService;

    @Test
    void findByTemplateName() {
        ChartTemplateConfigDto chartTemplateConfigDto = chartTemplateConfigService.findByConfigName("test");

        log.info(JSON.toJSONString(chartTemplateConfigDto));
    }

}