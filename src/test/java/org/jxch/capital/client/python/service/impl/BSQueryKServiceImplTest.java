package org.jxch.capital.client.python.service.impl;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson2.JSON;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.jxch.capital.client.domain.mapper.KLineMapper;
import org.jxch.capital.client.python.dto.BSQueryKParam;
import org.jxch.capital.client.stock.KLine;
import org.jxch.capital.client.uilt.FileU;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@Slf4j
@SpringBootTest
class BSQueryKServiceImplTest {
    @Autowired
    BSQueryKServiceImpl bsQueryKService;
    @Autowired
    KLineMapper kLineMapper;

    @Test
    void queryKLine() {
        BSQueryKParam param = BSQueryKParam.builder()
                .code("sh.600000")
                .csvFile(FileU.tmpFilePath("sh.600000.csv"))
                .startDate(DateUtil.date().offset(DateField.DAY_OF_YEAR, -40))
                .endDate(DateUtil.date())
                .frequency("5")
                .adjustflag("2")
                .fields("date,code,open,high,low,close,volume")
                .build();
        List<KLine> kLine = bsQueryKService.queryKLine2Convert(param);
        log.info(JSON.toJSONString(kLine));
    }

}