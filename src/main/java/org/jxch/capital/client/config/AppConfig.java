package org.jxch.capital.client.config;

import cn.hutool.core.io.FileUtil;
import jakarta.annotation.PostConstruct;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.io.File;
import java.util.Arrays;
import java.util.Objects;

@Data
@Slf4j
@Configuration
@ConfigurationProperties(prefix = "app")
public class AppConfig {
    private String dataPath;
    private String h2dbPath;
    private String pythonPath;
    private String pythonExecutorPath;
    private String pythonWinAmd64Url;
    private String tmpPath;

    public File getPythonExecutorFile() {
        return new File(pythonExecutorPath).toPath().resolve("python.exe").toFile();
    }

    public String getPythonExecutorAbsolutePath() {
        return getPythonExecutorFile().getAbsolutePath();
    }

    public String getPythonExecutorAppScriptsAbsolutePath() {
        return getPythonExecutorFile().toPath().getParent().resolve("app-scripts").toAbsolutePath().toString();
    }

    @PostConstruct
    public void init() {
        File pythonPath = new File(getPythonPath());
        if (pythonPath.exists()) {
            File[] files = pythonPath.listFiles();
            if (Objects.nonNull(files)) {
                File pythonFile = Arrays.stream(files).filter(file -> file.getName().contains("python-")).findAny().orElse(null);
                if (Objects.nonNull(pythonFile)) {
                    setPythonExecutorPath(pythonFile.getAbsolutePath());
                    if (!new File(getPythonExecutorAppScriptsAbsolutePath()).exists()) {
                        FileUtil.mkdir(getPythonExecutorAppScriptsAbsolutePath());
                    }
                }
            }
        }
        if (!new File(getTmpPath()).exists()) {
            FileUtil.mkdir(getTmpPath());
        }
    }

}
