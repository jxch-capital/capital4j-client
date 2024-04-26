package org.jxch.capital.client.service.impl;

import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson2.JSON;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.jxch.capital.client.db.dto.KLineHisCNDto;
import org.jxch.capital.client.db.sharding.k_line_his_cn.dao.KLineHisCN2015Repository;
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
    @Autowired
    private KLineHisCN2015Repository kLineHisCN2015Repository;

    @Test
    void findByCodeAndDateBetween() {
        List<KLineHisCNDto> dtoList = kLineHisCNService.findByCodeAndDateBetween(1,
                DateUtil.parse("2015-01-05", "yyyy-MM-dd"), DateUtil.parse("2015-08-05", "yyyy-MM-dd"));
        log.info(JSON.toJSONString(dtoList));
    }

    @Test
    void saveAll() {
        Integer total = kLineHisCNService.saveAll(List.of(KLineHisCNDto.builder()
                .code(1)
                .ex("tt")
                .date(DateUtil.parse("2015-05-05", "yyyy-MM-dd"))
                .build()));
        log.info("{}", total);
    }

    @Test
    void findByCodeAndDateBetween2015() {
        var dtoList = kLineHisCN2015Repository.findAllByCodeAndDateBetween(1,
                DateUtil.parse("2015-01-05", "yyyy-MM-dd"), DateUtil.parse("2015-08-05", "yyyy-MM-dd"));
        log.info(JSON.toJSONString(dtoList));
    }

}