package org.jxch.capital.client.python.executor;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
class PythonExecutorTest {
    @Autowired
    PythonExecutor pythonExecutor;

    @Test
    void run() {
        String csvName = pythonExecutor.run(List.of("D:\\jxch-capital\\capital4j-client\\src\\main\\resources\\python\\baostock_query_history_k_data_plus_2csv.py",
                "--code", "sh.600000", "--csv_file", "G:\\data\\development\\capital4j-client\\tmp\\test.csv", "--start_date", "2024-03-01"
        ));
        log.info(csvName);
    }
}