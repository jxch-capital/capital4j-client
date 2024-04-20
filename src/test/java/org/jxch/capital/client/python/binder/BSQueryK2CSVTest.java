package org.jxch.capital.client.python.binder;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.jxch.capital.client.config.AppConfig;
import org.jxch.capital.client.python.dto.BSQueryKParam;
import org.jxch.capital.client.uilt.FileU;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest
class BSQueryK2CSVTest {
    @Autowired
    private BSQueryK2CSV bsQueryK2CSV;
    @Autowired
    private AppConfig appConfig;

    @Test
    public void testK2CSV() {
        BSQueryKParam param = BSQueryKParam.builder()
                .code("sh.600000")
                .csvFile(FileU.tmpFilePath("sh.600000.csv"))
                .startDate(DateUtil.date().offset(DateField.DAY_OF_YEAR, -20))
                .endDate(DateUtil.date())
                .build();

        bsQueryK2CSV.run(param);
    }

}