package org.jxch.capital.client.fx.dashboard.impl;

import cn.hutool.cache.CacheUtil;
import cn.hutool.cache.impl.TimedCache;
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
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@RequiredArgsConstructor
public class PYPlotChartTemplate implements ChartTemplate {
    private final TimedCache<String, String> cache = CacheUtil.newTimedCache(TimeUnit.DAYS.toMillis(1));
    private final PythonExecutor pythonExecutor;

    @Override
    @SneakyThrows
    public void chart(@NonNull ChartParam chartParam, String dataParam) {
        String tmpFile = cache.get(chartParam.getChartParam());

        if (Objects.nonNull(tmpFile)) {
            NodeU.loadImage(tmpFile, chartParam.getBoard());
        } else {
            String outputFilePath = FileU.tmpFilePath(UUID.randomUUID() + ".png");
            pythonExecutor.runCode(chartParam.getChartParam(), List.of("-o", outputFilePath));
            NodeU.loadImage(outputFilePath, chartParam.getBoard());
            cache.put(chartParam.getChartParam(), outputFilePath);
        }
    }

    @Override
    public String dataParamTemplate() {
        return "";
    }

    @Override
    public String chartParamTemplate() {
        return """
                import matplotlib.pyplot as plt
                import pandas as pd
                import mplfinance as mpf
                import argparse

                parser = argparse.ArgumentParser(description='akshare: ak_unemployment [失业率]')
                parser.add_argument('--output_file', '-o', type=str, required=True, help="输出图片文件的绝对路径")
                args = parser.parse_args()
                output_file = args.output_file
                
                """;
    }

    @Override
    public String getName() {
        return "PY-Plot";
    }

}
