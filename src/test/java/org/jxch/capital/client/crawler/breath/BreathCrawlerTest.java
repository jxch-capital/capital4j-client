package org.jxch.capital.client.crawler.breath;

import com.alibaba.fastjson2.JSON;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.jxch.capital.client.crawler.dto.Breath;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
@ActiveProfiles("spring-boot-test")
class BreathCrawlerTest {
    @Autowired
    BreathCrawler breathCrawler;

    @Test
    void breath() {
        Breath breath = breathCrawler.breath();
        log.info(JSON.toJSONString(breath));
    }
}