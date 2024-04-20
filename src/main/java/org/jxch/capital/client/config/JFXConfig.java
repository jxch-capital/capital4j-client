package org.jxch.capital.client.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
public class JFXConfig {
    @Value("${spring.fx.fxml-scan:/**/*.fxml}")
    private String fxmlScan;
    @Value("${spring.fx.css-scan:/**/*.css}")
    private String cssScan;
    @Value("${spring.fx.py-scan:/**/*.py}")
    private String pyScan;
}
