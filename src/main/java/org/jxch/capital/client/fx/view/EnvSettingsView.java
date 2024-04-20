package org.jxch.capital.client.fx.view;

import jakarta.annotation.Resource;
import javafx.fxml.Initializable;
import javafx.scene.text.Text;
import lombok.SneakyThrows;
import org.jxch.capital.client.config.AppConfig;
import org.jxch.capital.client.event.OperationalEvent;
import org.jxch.capital.client.python.executor.PythonExecutor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

@Component
public class EnvSettingsView implements Initializable {
    @Resource
    private AppConfig appConfig;
    @Resource
    private ApplicationEventPublisher publisher;
    @Resource
    private PythonExecutor pythonExecutor;
    public Text pythonVersion;
    public Text systemInfo;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        refreshInfo();
    }

    public void refreshInfo() {
        String osName = System.getProperty("os.name").toLowerCase();
        String osArch = System.getProperty("os.arch").toLowerCase();
        systemInfo.setText(String.format("%s %s", osName, osArch));

        if (Objects.nonNull(appConfig.getPythonExecutorPath())) {
            pythonVersion.setText(appConfig.getPythonExecutorPath());
        } else {
            pythonVersion.setText("先下载，再初始化");
        }
    }

    @SneakyThrows
    public void downloadPython() {
        pythonExecutor.downloadEnv();
        publisher.publishEvent(new OperationalEvent("Python环境下载成功"));
        refreshInfo();
    }


    public void initPythonEnv() {
        pythonExecutor.initPythonEnv();
        publisher.publishEvent(new OperationalEvent("Python环境初始化成功"));
    }

}
