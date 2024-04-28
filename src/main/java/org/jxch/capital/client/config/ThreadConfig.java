package org.jxch.capital.client.config;

import jakarta.annotation.PreDestroy;
import lombok.Data;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Data
@Configuration
public class ThreadConfig {
    public final static String VIRTUAL_THREAD_POOL = "VIRTUAL_THREAD_POOL";
    private List<ExecutorService> executorServiceList = new ArrayList<>();

    @Bean(name = VIRTUAL_THREAD_POOL)
    public ExecutorService virtualThreadPool() {
        return Executors.newVirtualThreadPerTaskExecutor();
    }

    @PreDestroy
    public void preDestroy(){
        executorServiceList.forEach(ExecutorService::shutdown);
    }

}
