package org.jxch.capital.client.db.pg.sharding.dao;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.TimeInterval;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.jxch.capital.client.db.pg.sharding.po.KHashCN5M5L;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

@Slf4j
@SpringBootTest
@ActiveProfiles("spring-boot-test")
class KHashCN5M5LRepositoryTest {
    @Autowired
    private KHashCN5M5LRepository repository;

    @Test
    void findByPrefixIdBetween() {
        TimeInterval timer = DateUtil.timer();
        List<KHashCN5M5L> poList = repository.findByPrefixIdBetween("11114", 100, 100);
        log.info("{}", poList.size());
        log.info(timer.intervalPretty());
    }

}