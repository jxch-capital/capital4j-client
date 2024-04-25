package org.jxch.capital.client.fx.template;

import cn.hutool.cache.CacheUtil;
import cn.hutool.cache.impl.FIFOCache;
import cn.hutool.core.annotation.AnnotationUtil;
import cn.hutool.core.util.ReflectUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.alibaba.fastjson2.JSON;
import com.google.common.io.CharStreams;
import jakarta.annotation.PostConstruct;
import lombok.NonNull;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.jxch.capital.client.event.PaneTemplateRemoveCacheEvent;
import org.jxch.capital.client.uilt.ResourceU;
import org.springframework.context.event.EventListener;
import org.springframework.core.io.Resource;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledFuture;
import java.util.stream.Collectors;

@Slf4j
@Aspect
@Component
public class ParentTemplateAop {
    private static final FIFOCache<String, Object> CACHE = CacheUtil.newFIFOCache(200);
    private static final Map<String, ScheduledFuture<?>> SCHEDULED_FUTURES = new ConcurrentHashMap<>();
    private static final ThreadPoolTaskScheduler SCHEDULER = new ThreadPoolTaskScheduler();

    @SneakyThrows
    private String getParam(Class<?> templateParamType, String templateParamValue) {
        if (Objects.equals(templateParamType, String.class)) {
            return templateParamValue;
        }

        if (Objects.equals(templateParamType, Resource.class)) {
            return ResourceU.readResource(templateParamValue);
        }

        if (Objects.equals(templateParamType, File.class)) {
            return Files.readString(new File(templateParamValue).toPath());
        }

        if (Objects.equals(templateParamType, Path.class)) {
            return Files.readString(Path.of(templateParamValue));
        }

        if (Objects.equals(templateParamType, URL.class)) {
            return CharStreams.toString(new InputStreamReader(URI.create(templateParamValue).toURL().openStream()));
        }

        if (Objects.equals(templateParamType, URI.class)) {
            return CharStreams.toString(new InputStreamReader(URI.create(templateParamValue).toURL().openStream()));
        }

        return JSON.toJSONString(ReflectUtil.newInstance(templateParamType));
    }

    private ParentTemplateService getAnn(@NonNull ProceedingJoinPoint joinPoint) {
        return AnnotationUtil.getAnnotation(joinPoint.getTarget().getClass(), ParentTemplateService.class);
    }

    @SneakyThrows
    @Around("execution(* org.jxch.capital.client.fx.template.ParentTemplate.getTemplateParam(..))")
    public Object getTemplateParam(@NonNull ProceedingJoinPoint joinPoint) {
        String param = (String) joinPoint.proceed();
        ParentTemplateService ann = getAnn(joinPoint);
        return Objects.nonNull(param) ? param :
                getParam(ann.templateParamType(), ann.templateParamValue());
    }

    @SneakyThrows
    @Around("execution(* org.jxch.capital.client.fx.template.ParentTemplate.getScriptParam(..))")
    public Object getScriptParam(@NonNull ProceedingJoinPoint joinPoint) {
        String param = (String) joinPoint.proceed();
        ParentTemplateService ann = getAnn(joinPoint);
        return Objects.nonNull(param) ? param :
                getParam(ann.scriptParamType(), ann.scriptParamValue());
    }

    @SneakyThrows
    @Around("execution(* org.jxch.capital.client.fx.template.ParentTemplate.template(..))")
    public Object template(@NonNull ProceedingJoinPoint joinPoint) {
        ParentTemplateService ann = getAnn(joinPoint);
        if (ann.cache()) {
            final String key = SecureUtil.md5(((ParentTemplate)SpringUtil.getBean(joinPoint.getTarget().getClass())).getName()
                    + Arrays.stream(joinPoint.getArgs()).map(Object::toString).collect(Collectors.joining("")));
            Object value = CACHE.get(key);
            if (Objects.isNull(value)) {
                value = joinPoint.proceed(joinPoint.getArgs());
                putCache(key, value, ann.cacheExpirationCorn());
            }
            return value;
        }

        return joinPoint.proceed(joinPoint.getArgs());
    }

    @EventListener
    public void removeCacheEvent(@NonNull PaneTemplateRemoveCacheEvent event) {
        removeCache(SecureUtil.md5(SpringUtil.getBean(event.getClazz()).getName() + event.getTemplateParam() + event.getScriptParam()));
    }

    private void putCache(String key, Object value, String corn) {
        removeCache(key);
        CACHE.put(key, value);
        SCHEDULED_FUTURES.put(key, SCHEDULER.schedule(() -> removeCache(key), new CronTrigger(corn)));
    }

    private void removeCache(String key) {
        if (Objects.nonNull(CACHE.get(key))) {
            CACHE.remove(key);
        }
        if (Objects.nonNull(SCHEDULED_FUTURES.get(key))) {
            SCHEDULED_FUTURES.get(key).cancel(true);
            SCHEDULED_FUTURES.remove(key);
        }
    }

    @PostConstruct
    public void init() {
        SCHEDULER.initialize();
    }

}
