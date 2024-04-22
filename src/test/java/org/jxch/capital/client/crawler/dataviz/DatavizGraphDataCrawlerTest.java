package org.jxch.capital.client.crawler.dataviz;

import com.alibaba.fastjson2.JSON;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.jxch.capital.client.crawler.dto.DatavizGraph;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@Slf4j
@SpringBootTest
@ActiveProfiles("spring-boot-test")
class DatavizGraphDataCrawlerTest {
    @Autowired
    private DatavizGraphDataCrawler datavizGraphDataCrawler;

    @Test
    void graphData() {
        DatavizGraph datavizGraph = datavizGraphDataCrawler.graphData();
        log.info(JSON.toJSONString(datavizGraph));
    }
}