package org.jxch.capital.client.khash.rocks;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.TimeInterval;
import com.alibaba.fastjson2.JSON;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.jxch.capital.client.stock.dto.KLine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.io.File;
import java.util.List;

@Slf4j
@SpringBootTest
@ActiveProfiles("spring-boot-test")
class CsvBaostock5M2RocksDBTest {
    @Autowired
    private CsvBaostock5M2RocksDB csvBaostock5M2RocksDB;

    @Test
    void toRocksDB() {
        TimeInterval timer = DateUtil.timer();
        String testFile = "G:\\app\\backup\\data\\stock_data\\csv\\5-2\\sh.600000_19900101-20231231.csv";
        csvBaostock5M2RocksDB.toRocksDB(List.of(new File(testFile)));
        log.info("time: {}s.", timer.intervalSecond());
    }

    @Test
    void find() {
        TimeInterval timer = DateUtil.timer();
        List<KLine> kLines = csvBaostock5M2RocksDB.find("sh.600000", DateUtil.parse("20131023", "yyyyMMdd"));
        log.info(JSON.toJSONString(kLines));
        log.info("time: {}s.", timer.intervalSecond());
    }

}