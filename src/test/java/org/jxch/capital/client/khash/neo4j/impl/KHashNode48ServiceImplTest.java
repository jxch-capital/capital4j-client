package org.jxch.capital.client.khash.neo4j.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.TimeInterval;
import com.alibaba.fastjson2.JSON;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.jxch.capital.client.khash.neo4j.KHashNode;
import org.jxch.capital.client.khash.neo4j.KHashNodeData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
@Slf4j
@SpringBootTest
@ActiveProfiles("spring-boot-test")
class KHashNode48ServiceImplTest {
    @Autowired
    private KHashNode48ServiceImpl service;

    @Test
    void save() {
        TimeInterval timer = DateUtil.timer();
        KHashNode kHashNode = service.save(List.of("4", "9", "4", "1"), KHashNodeData.builder()
                .code("test2").date(DateUtil.date()).build());
//        log.info(JSON.toJSONString(kHashNode));
        log.info(timer.intervalPretty());
    }

    @Test
    void get() {
        TimeInterval timer = DateUtil.timer();
        List<KHashNodeData> kHashNodeData = service.get(List.of("4", "9"));
        log.info("{}", kHashNodeData.size());
        log.info(JSON.toJSONString(kHashNodeData));
        log.info(timer.intervalPretty());
    }
}