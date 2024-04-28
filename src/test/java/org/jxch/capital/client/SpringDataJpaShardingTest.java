package org.jxch.capital.client;

import cn.hutool.extra.spring.SpringUtil;
import com.alibaba.fastjson2.JSON;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@Slf4j
@SpringBootTest
@ActiveProfiles("spring-boot-test")
public class SpringDataJpaShardingTest {

    @Test
    @SneakyThrows
    public void test() {
        Object webConfigTest = SpringUtil.getBean("webConfigTest");
        log.info(JSON.toJSONString(webConfigTest));
    }


}
