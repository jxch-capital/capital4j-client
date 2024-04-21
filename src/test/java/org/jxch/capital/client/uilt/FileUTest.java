package org.jxch.capital.client.uilt;

import com.alibaba.fastjson2.JSON;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.io.File;
import java.util.Arrays;
import java.util.Objects;

@Slf4j
@SpringBootTest
@ActiveProfiles("spring-boot-test")
class FileUTest {

    @Test
    void unTarGz2tmp() {
        File tmp = FileU.unTarGz2tmp(new File("C:\\Users\\xiche\\Downloads\\jsonpath-0.82.tar.gz"));
        tmp.toPath().resolve("jsonpath-0.82").toFile();
        log.info(JSON.toJSONString(Arrays.stream(Objects.requireNonNull(tmp.listFiles())).map(File::getName).toList()));

    }

}