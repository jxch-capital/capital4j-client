package org.jxch.capital.client.uilt;

import lombok.SneakyThrows;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import java.nio.file.Files;

public class ResourceU {
    private static final ResourcePatternResolver RESOLVER = new PathMatchingResourcePatternResolver();

    @SneakyThrows
    public static String readResource(String location) {
        return Files.readString(RESOLVER.getResource(location).getFile().toPath());
    }


}
