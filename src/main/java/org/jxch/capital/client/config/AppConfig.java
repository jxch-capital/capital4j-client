package org.jxch.capital.client.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.io.File;

@Data
@Configuration
@ConfigurationProperties(prefix = "app")
public class AppConfig {
    private String dataPath;
    private String h2dbPath;
    private String pythonPath;
    private String pythonExecutorPath;

    private String pythonWinAmd64;

    public File getPythonExecutorFile() {
        return new File(pythonExecutorPath).toPath().resolve("python.exe").toFile();
    }

}
