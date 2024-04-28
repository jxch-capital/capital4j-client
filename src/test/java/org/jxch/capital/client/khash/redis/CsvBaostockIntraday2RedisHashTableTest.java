package org.jxch.capital.client.khash.redis;

import com.alibaba.fastjson2.JSON;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.jxch.capital.client.stock.dto.KLine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.io.File;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
@ActiveProfiles("spring-boot-test")
class CsvBaostockIntraday2RedisHashTableTest {
    @Autowired
    private CsvBaostockIntraday2RedisHashTable csvBaostockIntraday2RedisHashTable;

    @Test
    void toRedis() {
        String testFile = "G:\\app\\backup\\data\\stock_data\\csv\\5-2\\sh.600000_19900101-20231231.csv";
        csvBaostockIntraday2RedisHashTable.toRedis(List.of(new File(testFile)));
    }

    @Test
    void find() {
        List<KLine> kLines = csvBaostockIntraday2RedisHashTable.find("20100419");
        log.info(JSON.toJSONString(kLines));
    }

}