package org.jxch.capital.client.service;

import cn.hutool.extra.spring.SpringUtil;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class NamedOrderedServices {

    public static <T extends NamedOrderedService> Map<String, T> allServices(Class<T> serviceClass) {
        return SpringUtil.getBeansOfType(serviceClass).values().stream().collect(Collectors.toMap(NamedOrderedService::getName, Function.identity()));
    }

    public static  <T extends NamedOrderedService> List<T> allSortedServices(Class<T> serviceClass) {
        return SpringUtil.getBeansOfType(serviceClass).values().stream().sorted(Comparator.comparing(NamedOrderedService::getOrder)).toList();
    }

    public static <T extends NamedOrderedService> List<String> allSortedServiceNames(Class<T> serviceClass) {
        return allSortedServices(serviceClass).stream().map(NamedOrderedService::getName).toList();
    }

    public static <T extends NamedOrderedService> T findServiceByName(Class<T> serviceClass, String name) {
        return allServices(serviceClass).get(name);
    }

}
