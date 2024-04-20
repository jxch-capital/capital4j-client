package org.jxch.capital.client.uilt;

import lombok.NonNull;
import lombok.SneakyThrows;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class ReflectionsU {

    @NonNull
    @SneakyThrows
    public static List<Class<?>> scanAllClass(@NonNull String packagePath) {
        String classpath = "classpath*:" + packagePath.replaceAll("\\.", "/") + "/**/*.class";
        List<Class<?>> classifierClass = new ArrayList<>();
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        Resource[] resources = resolver.getResources(classpath);
        for (Resource resource : resources) {
            MetadataReader metadataReader = new CachingMetadataReaderFactory().getMetadataReader(resource);
            Class<?> clazz = Class.forName(metadataReader.getClassMetadata().getClassName());
            classifierClass.add(clazz);
        }
        return classifierClass;
    }

    public static List<Class<?>> scanAllClass(@NonNull String packagePath, @NonNull Function<Class<?>, Boolean> classFilter) {
        return scanAllClass(packagePath).stream().filter(classFilter::apply).toList();
    }

    @NonNull
    @SuppressWarnings("unchecked")
    public static <T> T createJDKProxyByInterface(@NonNull Class<T> type, InvocationHandler invocationHandler) {
        return (T) Proxy.newProxyInstance(type.getClassLoader(), new Class<?>[] { type }, invocationHandler);
    }

}
