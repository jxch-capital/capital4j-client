package org.jxch.capital.client.service.impl;

import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson2.JSON;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.jxch.capital.client.db.dto.KLineHisCN5mDto;
import org.jxch.capital.client.db.sharding.k_line_his_cn_5m.dao.KLineHisCN5m2015Repository;
import org.jxch.capital.client.service.KLineHisCN5mService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

@Slf4j
@SpringBootTest
@ActiveProfiles("spring-boot-test")
class KLineHisCN5mServiceImplTest {
    @Autowired
    private KLineHisCN5mService kLineHisCN5mService;
    @Autowired
    private KLineHisCN5m2015Repository kLineHisCN2015Repository;

    @Test
    void findByCodeAndDateBetween() {
        List<KLineHisCN5mDto> dtoList = kLineHisCN5mService.findByCodeAndDateBetween(1,
                DateUtil.parse("2015-01-05", "yyyy-MM-dd"), DateUtil.parse("2015-08-05", "yyyy-MM-dd"));
        log.info(JSON.toJSONString(dtoList));
    }

    @Test
    void saveAll() {
        Integer total = kLineHisCN5mService.saveAll(List.of(KLineHisCN5mDto.builder()
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

    @Test
    void del() {
        kLineHisCN5mService.delByCodeAndDateBetween(1,
                DateUtil.parse("2015-01-05", "yyyy-MM-dd"), DateUtil.parse("2015-08-05", "yyyy-MM-dd"));
        findByCodeAndDateBetween2015();
    }

}