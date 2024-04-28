package org.jxch.capital.client.fx.progress;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.TimeInterval;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
class ProgressBarPaneTest {


    @Test
    @SneakyThrows
    public void test() {
        TimeInterval timer = DateUtil.timer();
        TimeUnit.SECONDS.sleep(10);
        log.info("{}", timer.intervalPretty());
    }

}