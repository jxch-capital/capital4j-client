package org.jxch.capital.client.python.binder;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.jxch.capital.client.config.AppConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;

@Slf4j
@SpringBootTest
class BSQueryK2CSVTest {
    @Autowired
    private BSQueryK2CSV bsQueryK2CSV;
    @Autowired
    private AppConfig appConfig;

    @Test
    public void testK2CSV() {
        bsQueryK2CSV.run("--code", "sh.000006", "--csv_file", new File(appConfig.getTmpPath()).toPath().resolve("test2.csv").toFile().getAbsolutePath());
    }

}