package org.jxch.capital.client.fx.dashboard.impl;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.jxch.capital.client.fx.dashboard.ChartTemplate;
import org.jxch.capital.client.fx.dto.ChartParam;
import org.jxch.capital.client.fx.util.NodeU;
import org.jxch.capital.client.python.executor.PythonExecutor;
import org.jxch.capital.client.uilt.FileU;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class PYPlotChartTemplate implements ChartTemplate {
    private final PythonExecutor pythonExecutor;

    @Override
    @SneakyThrows
    public void chart(@NonNull ChartParam chartParam, String dataParam) {
        String outputFilePath = FileU.tmpFilePath(UUID.randomUUID() + ".png");
        pythonExecutor.runCode(chartParam.getChartParam(), List.of("-o", outputFilePath));
        NodeU.loadImage(outputFilePath, chartParam.getBoard());
    }

    @Override
    public String dataParamTemplate() {
        return "";
    }

    @Override
    public String chartParamTemplate() {
        return """
                
                """;
    }

    @Override
    public String getName() {
        return "PY-Plot";
    }

}
