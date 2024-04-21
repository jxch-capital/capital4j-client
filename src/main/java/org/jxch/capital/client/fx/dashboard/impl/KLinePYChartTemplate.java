package org.jxch.capital.client.fx.dashboard.impl;


import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONWriter;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.jxch.capital.client.fx.dashboard.ChartTemplate;
import org.jxch.capital.client.fx.dto.ChartParam;
import org.jxch.capital.client.python.executor.PythonExecutor;
import org.jxch.capital.client.python.service.BSQueryKService;
import org.jxch.capital.client.stock.dto.StockQueryParam;
import org.jxch.capital.client.uilt.FileU;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class KLinePYChartTemplate implements ChartTemplate {
    private final BSQueryKService bsQueryKService;
    private final PythonExecutor pythonExecutor;

    @Override
    @SneakyThrows
    public void chart(@NonNull ChartParam chartParam, String dataParam) {
        String inputFilePath = bsQueryKService.downloadKLine(JSON.parseObject(dataParam, StockQueryParam.class));
        String outputFilePath = FileU.tmpFilePath(UUID.randomUUID() + ".png");

        File tmpPy = FileU.writeString2tmpFile(chartParam.getChartParam(), ".py");
        pythonExecutor.run(tmpPy, List.of("-i", inputFilePath, "-o", outputFilePath));

        ImageView imageView = new ImageView(new Image(new File(outputFilePath).toURI().toURL().toExternalForm()));

        chartParam.getBoard().getChildren().clear();
        chartParam.getBoard().getChildren().add(imageView);
    }

    @Override
    public String dataParamTemplate() {
        return JSON.toJSONString(new StockQueryParam(), JSONWriter.Feature.PrettyFormat, JSONWriter.Feature.WriteNulls);
    }

    @Override
    public String chartParamTemplate() {
        return """
                import argparse
                import mplfinance as mpf
                import pandas as pd
                
                parser = argparse.ArgumentParser(description='绘制K线图')
                parser.add_argument('--input_csv_file', '-i', type=str, required=True, help="输入股票数据csv文件的绝对路径")
                parser.add_argument('--output_file', '-o', type=str, required=True, help="输出图片文件的绝对路径")
                
                args = parser.parse_args()
                input_file = args.input_csv_file
                output_file = args.output_file
                
                def draw_k_line_chart(data, image_path, width=10, height=6, dpi=100):
                    mpf_style = mpf.make_mpf_style(base_mpf_style='charles', rc={'font.size': 8})
                    fig, ax = mpf.plot(data,type='candle',style=mpf_style,returnfig=True,volume=True,figsize=(width, height))
                    fig.savefig(image_path, dpi=dpi, bbox_inches="tight")
                    print(f"图片已保存至{image_path}")
                
                df = pd.read_csv(input_file)
                df.set_index('Date', inplace=True)
                draw_k_line_chart(df, output_file, width=10, height=6, dpi=100)
                """;
    }

    @Override
    public String getName() {
        return "Py-K线";
    }

}
