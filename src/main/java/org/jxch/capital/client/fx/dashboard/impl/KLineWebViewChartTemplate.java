package org.jxch.capital.client.fx.dashboard.impl;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONWriter;
import javafx.scene.web.WebView;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.jxch.capital.client.fx.dashboard.ChartTemplate;
import org.jxch.capital.client.fx.dto.ChartParam;
import org.jxch.capital.client.stock.StockService;
import org.jxch.capital.client.stock.dto.KLine;
import org.jxch.capital.client.stock.dto.StockQueryParam;
import org.jxch.capital.client.uilt.FileU;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class KLineWebViewChartTemplate implements ChartTemplate {
    private final StockService stockService;

    @Override
    public String getName() {
        return "WebView-K线";
    }

    @Override
    @SneakyThrows
    public void chart(@NonNull ChartParam chartParam, String dataParam) {
        List<KLine> kLines = stockService.query(JSON.parseObject(dataParam, StockQueryParam.class));
        var data =  kLines.stream().map(kLine -> List.of(kLine.getDate().getTime(), kLine.getOpen(), kLine.getHigh(), kLine.getLow(), kLine.getClose(), kLine.getVolume())).toList();
        File tmp = FileU.writeString2tmpFile(chartParam.getChartParam(), ".html");
        WebView webView = new WebView();
        webView.setVisible(false);
        webView.getEngine().load(tmp.toURI().toURL().toExternalForm());
        webView.getEngine().getLoadWorker().stateProperty().addListener((observable, oldValue, newValue) -> {
            webView.getEngine().executeScript("init('" + JSON.toJSONString(data) + "')");
            webView.setVisible(true);
        });
        chartParam.getBoard().getChildren().clear();
        chartParam.getBoard().getChildren().add(webView);
    }

    @Override
    public String dataParamTemplate() {
        return JSON.toJSONString(new StockQueryParam(), JSONWriter.Feature.PrettyFormat, JSONWriter.Feature.WriteNulls);
    }

    @Override
    public String chartParamTemplate() {
        return "";
    }

}
