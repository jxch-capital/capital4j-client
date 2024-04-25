package org.jxch.capital.client.service.impl;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson2.JSON;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.jxch.capital.client.db.dto.KLineHisCNDto;
import org.jxch.capital.client.service.KLineHisCNService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

@Slf4j
@SpringBootTest
@ActiveProfiles("spring-boot-test")
class KLineHisCNServiceImplTest {
    @Autowired
    private KLineHisCNService kLineHisCNService;

    @Test
    void findByCodeAndDateBetween() {
        List<KLineHisCNDto> dtoList = kLineHisCNService.findByCodeAndDateBetween(1,
                DateUtil.date().offset(DateField.DAY_OF_YEAR, -2), DateUtil.date().offset(DateField.DAY_OF_YEAR, 2));
        log.info(JSON.toJSONString(dtoList));
    }

    @Test
    void saveAll() {
        Integer total = kLineHisCNService.saveAll(List.of(KLineHisCNDto.builder()
                .code(1)
                .ex("tt")
                .date(DateUtil.date())
                .build()));
        log.info("{}", total);
    }
}