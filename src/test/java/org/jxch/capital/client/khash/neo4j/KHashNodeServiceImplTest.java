package org.jxch.capital.client.khash.neo4j;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.TimeInterval;
import com.alibaba.fastjson2.JSON;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.jxch.capital.client.db.neo4j.node.KHashNode;
import org.jxch.capital.client.db.neo4j.node.KHashNodeData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Map;

@Slf4j
@SpringBootTest
@ActiveProfiles("spring-boot-test")
class KHashNodeServiceImplTest {
    @Autowired
    private KHashNodeService service;

    @Test
    void save() {
        TimeInterval timer = DateUtil.timer();
        KHashNode kHashNode = service.save(List.of("4", "1", "4", "1"), KHashNodeData.builder()
                .code("test2").date(DateUtil.date()).build());
//        log.info(JSON.toJSONString(kHashNode));
        log.info(timer.intervalPretty());
    }

    @Test
    void get() {
        TimeInterval timer = DateUtil.timer();
        List<KHashNodeData> kHashNodeData = service.get(List.of("1", "3", "4"));
        log.info("{}", kHashNodeData.size());
        log.info(JSON.toJSONString(kHashNodeData));
        log.info(timer.intervalPretty());
    }

    @Test
    public void test() {
        Map<String, Object> test2 =
                BeanUtil.beanToMap(KHashNodeData.builder().code("test2").date(DateUtil.date()).build());
        log.info(JSON.toJSONString(test2));
    }

}