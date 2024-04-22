package org.jxch.capital.client.python.executor.impl;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.file.FileAppender;
import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.ZipUtil;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.jxch.capital.client.config.AppConfig;
import org.jxch.capital.client.event.PyExecutorPrintEvent;
import org.jxch.capital.client.exception.OperationalException;
import org.jxch.capital.client.python.executor.PythonExecutor;
import org.jxch.capital.client.uilt.FileU;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class PythonExecutorImpl implements PythonExecutor {
    private final ApplicationEventPublisher publisher;
    private final AppConfig appConfig;

    @Override
    @SneakyThrows
    public void downloadEnv() {
        String osName = System.getProperty("os.name").toLowerCase();
        String osArch = System.getProperty("os.arch").toLowerCase();
        if (osName.contains("win")) {
            if (osArch.contains("amd64")) {
                URL website = new URL(appConfig.getPythonWinAmd64Url());
                String[] paths = appConfig.getPythonWinAmd64Url().split("/");
                String targetFileName = paths[paths.length - 1];
                Path targetPath = new File(appConfig.getPythonPath()).toPath().resolve(targetFileName);
                Files.createDirectories(targetPath.getParent());
                try (InputStream in = website.openStream()) {
                    Files.copy(in, targetPath, StandardCopyOption.REPLACE_EXISTING);
                }
                ZipUtil.unzip(targetPath.toFile(), targetPath.getParent().resolve(StrUtil.removeSuffix(targetFileName, ".zip")).toFile());
                Files.deleteIfExists(targetPath);
            }
        } else {
            throw new OperationalException(String.format("暂不支持此类系统：%s %s", osName, osArch));
        }
    }

    @Override
    public void initPythonEnv() {
        importSite();
        installPip();
        upgradePip();
        pipInstalls();
    }

    @Override
    public String run(@NonNull List<String> command) {
        List<String> commandArr = new ArrayList<>(command);
        commandArr.add(0, appConfig.getPythonExecutorAbsolutePath());
        log.info(String.join(" ", commandArr));
        return runProcess(new ProcessBuilder(commandArr));
    }

    @NonNull
    @SneakyThrows
    private String runProcess(@NonNull ProcessBuilder pb) {
        pb.redirectErrorStream(true);
        Process process = pb.start();
        StringBuilder stringBuilder = new StringBuilder();
        try (Scanner in = new Scanner(new BufferedReader(new InputStreamReader(process.getInputStream())))) {
            while (in.hasNextLine()) {
                String line = in.nextLine();
                log.info(line);
                stringBuilder.append(line).append("\n");
                publisher.publishEvent(new PyExecutorPrintEvent(this).logMsg(line));
            }
            process.waitFor();
        }
        return stringBuilder.toString();
    }


    private void importSite() {
        String importSite = "import site";
        File pth = Arrays.stream(Objects.requireNonNull(appConfig.getPythonExecutorFile().toPath().getParent().toFile().listFiles()))
                .filter(file -> file.getName().startsWith("python") && file.getName().endsWith("._pth")).findFirst().orElseThrow();
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
        run(List.of("-m", "pip", "install", "--upgrade", "pip"));
    }

    private void pipInstalls() {
        run(List.of("-m", "pip", "install", "baostock", "-i", "https://pypi.tuna.tsinghua.edu.cn/simple"));
        run(List.of("-m", "pip", "install", "mplfinance", "-i", "https://pypi.tuna.tsinghua.edu.cn/simple"));
        pipInstallJsonPath();
        run(List.of("-m", "pip", "install", "akshare", "-i", "https://pypi.tuna.tsinghua.edu.cn/simple"));
    }

    private void pipInstallJsonPath() {
        File jsonpathFile = FileU.downloadFile2tmp("http://www.ultimate.com/phil/python/download/jsonpath-0.82.tar.gz");
        Path jsonpathPath = FileU.unTarGz2tmp(jsonpathFile).toPath().resolve("jsonpath-0.82");
        File setupPy = jsonpathPath.resolve("setup.py").toFile();

        List<String> lines = FileUtil.readUtf8Lines(setupPy);
        String content = lines.stream().map(line -> {
            if (Objects.equals(line.trim(), "import jsonpath as module")) {
                return "#import jsonpath as module";
            }
            if (Objects.equals(line.trim(), "name = module.__name__")) {
                return "name = \"jsonpath\"";
            }
            if (Objects.equals(line.trim(), "version = module.__version__")) {
                return "version = \"0.82\"";
            }
            if (Objects.equals(line.trim(), "lines = module.__doc__.split(\"\\n\")")) {
                return String.format("""
                        doc =  \"\"\"\n
                        %s
                        \n\"\"\"
                        lines = doc.split("\\n")
                        """, String.join("\n", FileUtil.readUtf8Lines(jsonpathPath.resolve("PKG-INFO").toFile())));
            }
            return line;
        }).collect(Collectors.joining("\n"));
        FileUtil.writeString(content, setupPy, Charset.defaultCharset());
        runProcess(new ProcessBuilder(appConfig.getPythonExecutorPipFile().getAbsolutePath(), "install", jsonpathPath.toFile().getAbsolutePath(), "--user"));
    }

}
