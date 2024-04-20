package org.jxch.capital.client.uilt;

import cn.hutool.extra.spring.SpringUtil;
import lombok.NonNull;
import org.jxch.capital.client.config.AppConfig;

import java.io.File;

public class FileU {

    @NonNull
    public static File tmpFile() {
        return new File(SpringUtil.getBean(AppConfig.class).getTmpPath());
    }

    @NonNull
    public static File tmpFile(String fileName) {
        return tmpFile().toPath().resolve(fileName).toFile();
    }

    @NonNull
    public static String tmpFilePath(String fileName) {
        return tmpFile().toPath().resolve(fileName).toFile().getAbsolutePath();
    }

}
