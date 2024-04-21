package org.jxch.capital.client.uilt;

import cn.hutool.core.io.FileUtil;
import cn.hutool.extra.spring.SpringUtil;
import lombok.NonNull;
import org.jxch.capital.client.config.AppConfig;

import java.io.File;
import java.nio.charset.Charset;
import java.util.UUID;

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

    public static File writeString2tmpFile(String text, String suffix) {
        return FileUtil.writeString(text, tmpFile(UUID.randomUUID() + suffix), Charset.defaultCharset());
    }

}
