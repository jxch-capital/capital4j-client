package org.jxch.capital.client.fx.view;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.file.FileAppender;
import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.ZipUtil;
import jakarta.annotation.Resource;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.text.Text;
import lombok.NonNull;
import lombok.SneakyThrows;
import org.jxch.capital.client.config.AppConfig;
import org.jxch.capital.client.event.OperationalEvent;
import org.jxch.capital.client.exception.OperationalException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.Scanner;

@Component
public class EnvSettingsView implements Initializable {
    private static final Logger log = LoggerFactory.getLogger(EnvSettingsView.class);
    @Resource
    private AppConfig appConfig;
    @Resource
    private ApplicationEventPublisher publisher;
    @Resource
    private LogView logView;
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

        pythonVersion.setText("无，需要下载");
        File pythonPath = new File(appConfig.getPythonPath());
        if (pythonPath.exists()) {
            File[] files = pythonPath.listFiles();
            if (Objects.nonNull(files)) {
                File pythonFile = Arrays.stream(files).filter(file -> file.getName().contains("python-")).findAny().orElse(null);
                if (Objects.nonNull(pythonFile)) {
                    pythonVersion.setText(pythonFile.getName());
                    appConfig.setPythonExecutorPath(pythonFile.getAbsolutePath());
                }
            }
        }
    }

    @SneakyThrows
    public void downloadPython(ActionEvent actionEvent) {
        String systemInfoText = systemInfo.getText();
        if (systemInfoText.contains("win")) {
            if (systemInfoText.contains("amd64")) {
                URL website = new URL(appConfig.getPythonWinAmd64());
                String[] paths = appConfig.getPythonWinAmd64().split("/");
                String targetFileName = paths[paths.length - 1];
                Path targetPath = new File(appConfig.getPythonPath()).toPath().resolve(targetFileName);
                Files.createDirectories(targetPath.getParent());
                try (InputStream in = website.openStream()) {
                    Files.copy(in, targetPath, StandardCopyOption.REPLACE_EXISTING);
                }
                ZipUtil.unzip(targetPath.toFile(), targetPath.getParent().resolve(StrUtil.removeSuffix(targetFileName, ".zip")).toFile());
                Files.deleteIfExists(targetPath);
                publisher.publishEvent(new OperationalEvent("Python环境下载成功"));
                refreshInfo();
            }
        } else {
            throw new OperationalException("暂不支持此类系统：" + systemInfoText);
        }
    }


    public void initPythonEnv(ActionEvent actionEvent) {
        importSite();
        installPip();
        upgradePip();
        pipInstalls();
        publisher.publishEvent(new OperationalEvent("Python环境初始化成功"));
    }

    private void importSite() {
        String importSite = "import site";
        File pth = appConfig.getPythonExecutorFile().toPath().getParent().resolve("python312._pth").toFile();
        if (FileUtil.readUtf8Lines(pth).stream().noneMatch(line -> Objects.equals(line.trim(), importSite))) {
            FileAppender fileAppender = new FileAppender(pth, 1, true);
            fileAppender.append("\n" + importSite);
            fileAppender.flush();
        }
    }

    @SneakyThrows
    private void installPip() {
        String python = appConfig.getPythonExecutorFile().getAbsolutePath();
        File pipFile = appConfig.getPythonExecutorFile().toPath().getParent().resolve("Scripts").resolve("pip3.exe").toFile();
        if (!pipFile.exists()) {
            Path getPipPy = appConfig.getPythonExecutorFile().toPath().getParent().resolve("get-pip.py");
            URL pipUrl = new URL("https://bootstrap.pypa.io/get-pip.py");
            try (InputStream in = pipUrl.openStream()) {
                Files.copy(in, getPipPy, StandardCopyOption.REPLACE_EXISTING);
            }
            runProcess(new ProcessBuilder(python, getPipPy.toFile().getAbsolutePath()));
        }
    }

    private void upgradePip() {
        String python = appConfig.getPythonExecutorFile().getAbsolutePath();
        runProcess(new ProcessBuilder(python, "-m", "pip", "install", "--upgrade", "pip"));
    }

    private void pipInstalls() {
        String python = appConfig.getPythonExecutorFile().getAbsolutePath();
        runProcess(new ProcessBuilder(python, "-m", "pip", "install", "baostock", "-i", "https://pypi.tuna.tsinghua.edu.cn/simple"));
    }

    @SneakyThrows
    private void runProcess(@NonNull ProcessBuilder pb) {
        pb.redirectErrorStream(true);
        Process process = pb.start();
        try (Scanner in = new Scanner(new BufferedReader(new InputStreamReader(process.getInputStream())))) {
            while (in.hasNextLine()) {
                String line = in.nextLine();
                logView.addLine(line);
                log.info(line);
            }
            process.waitFor();
        }
    }

}
