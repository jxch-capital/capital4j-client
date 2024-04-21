package org.jxch.capital.client.python.executor;

import cn.hutool.extra.spring.SpringUtil;
import lombok.NonNull;
import org.jxch.capital.client.python.register.PyBindRunnerParamProcessor;
import org.jxch.capital.client.uilt.FileU;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public interface PythonExecutor {

    void downloadEnv();

    void initPythonEnv();

    String run(List<String> command);

    default <P> String run(P param) {
        return run(SpringUtil.getBean(PyBindRunnerParamProcessor.class).encode(param));
    }

    default String run(@NonNull File pythonFile, List<String> command) {
        List<String> commandArr = new ArrayList<>(command);
        commandArr.add(0, pythonFile.getAbsolutePath());
        return run(commandArr);
    }

    default String run(@NonNull File pythonFile) {
        return run(List.of(pythonFile.getAbsolutePath()));
    }

    default String run(@NonNull File pythonFile, String... command) {
        List<String> commandArr = new ArrayList<>(List.of(command));
        commandArr.add(0, pythonFile.getAbsolutePath());
        return run(commandArr);
    }

    default <P> String run(@NonNull File pythonFile, P param) {
        List<String> commandArr = new ArrayList<>(SpringUtil.getBean(PyBindRunnerParamProcessor.class).encode(param));
        commandArr.add(0, pythonFile.getAbsolutePath());
        return run(commandArr);
    }

    default String runCode(String code, List<String> command) {
        File tmpPy = FileU.writeString2tmpFile(code, ".py");
        return run(tmpPy, command);
    }

}
