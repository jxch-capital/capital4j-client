package org.jxch.capital.client.config;

import cn.hutool.extra.spring.SpringUtil;
import jakarta.annotation.PreDestroy;
import lombok.Data;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Data
@Configuration
public class ThreadConfig {
    public final static String VIRTUAL_THREAD_POOL = "virtualThreadPool";

    @Bean(name = VIRTUAL_THREAD_POOL)
    public ExecutorService virtualThreadPool() {
        return Executors.newVirtualThreadPerTaskExecutor();
    }

    @PreDestroy
    public void preDestroy(){
        SpringUtil.getBean(VIRTUAL_THREAD_POOL, ExecutorService.class).close();
    }

}
