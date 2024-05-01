package org.jxch.capital.client.uilt;

import cn.hutool.extra.spring.SpringUtil;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;

public class ThreadU {

    @NotNull
    @Contract("_, _ -> new")
    public static <T> CompletionService<T> newCompletionService(String executorServiceBeanName, Class<T> clazz) {
        return new ExecutorCompletionService<T>(SpringUtil.getBean(executorServiceBeanName, ExecutorService.class));
    }

}
