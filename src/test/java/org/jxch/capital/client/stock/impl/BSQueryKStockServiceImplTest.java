package org.jxch.capital.client.stock.impl;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson2.JSON;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.jxch.capital.client.stock.dto.KLine;
import org.jxch.capital.client.stock.dto.StockQueryParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

@Slf4j
@SpringBootTest
@ActiveProfiles("spring-boot-test")
class BSQueryKStockServiceImplTest {
    @Autowired
    private BSQueryKStockServiceImpl stockService;

    @Test
    void query() {
        StockQueryParam param = StockQueryParam.builder()
                .codes(List.of("sh.600000"))
                .startDate(DateUtil.date().offset(DateField.DAY_OF_YEAR, -20))
                .build();

        List<KLine> kLines = stockService.queryOne(param);
        log.info(JSON.toJSONString(kLines));
    }

}