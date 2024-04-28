package org.jxch.capital.client.config;

import jakarta.annotation.PreDestroy;
import lombok.Data;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Data
@Configuration
@EnableAsync(proxyTargetClass = true)
public class ThreadConfig {
    public final static String VIRTUAL_THREAD_POOL = "VIRTUAL_THREAD_POOL";
    public final static String IO_THREAD_POOL = "IO_THREAD_POOL";
    private List<ExecutorService> executorServiceList = new ArrayList<>();

    @Bean(name = VIRTUAL_THREAD_POOL)
    public ExecutorService virtualThreadPool() {
        ExecutorService executorService = Executors.newVirtualThreadPerTaskExecutor();
        executorServiceList.add(executorService);
        return executorService;
    }

    @Bean(name = IO_THREAD_POOL)
    public ExecutorService threadPool() {
        ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() / 2);
        executorServiceList.add(executorService);
        return executorService;
    }

    @PreDestroy
    public void preDestroy(){
        executorServiceList.forEach(ExecutorService::shutdown);
    }

}
