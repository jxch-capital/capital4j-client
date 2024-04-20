package org.jxch.capital.client.python.executor;

import lombok.NonNull;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public interface PythonExecutor {

    void downloadEnv();

    void initPythonEnv();

    String run(List<String> command);

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

}
