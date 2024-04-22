package org.jxch.capital.client.fx.dashboard.impl;

import cn.hutool.cache.CacheUtil;
import cn.hutool.cache.impl.TimedCache;
import cn.hutool.crypto.SecureUtil;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONWriter;
import javafx.scene.web.WebView;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.jxch.capital.client.event.ChartTemplateCacheClearEvent;
import org.jxch.capital.client.fx.dashboard.ChartTemplate;
import org.jxch.capital.client.fx.dto.ChartParam;
import org.jxch.capital.client.stock.StockService;
import org.jxch.capital.client.stock.dto.KLine;
import org.jxch.capital.client.stock.dto.StockQueryParam;
import org.jxch.capital.client.uilt.FileU;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@RequiredArgsConstructor
public class KLineWebViewChartTemplate implements ChartTemplate {
    private final TimedCache<String, WebView> cache = CacheUtil.newTimedCache(TimeUnit.MINUTES.toMillis(30));
    private final StockService stockService;

    @Override
    public String getName() {
        return "WebView-K线";
    }

    @Override
    @SneakyThrows
    public void chart(@NonNull ChartParam chartParam, String dataParam) {
        String key = SecureUtil.md5(dataParam + chartParam.getChartParam());
        WebView webView = cache.get(key);

        if (Objects.isNull(webView)) {
            List<KLine> kLines = stockService.query(JSON.parseObject(dataParam, StockQueryParam.class));
            var data =  kLines.stream().map(kLine -> List.of(kLine.getDate().getTime(), kLine.getOpen(), kLine.getHigh(), kLine.getLow(), kLine.getClose(), kLine.getVolume())).toList();
            File tmp = FileU.writeString2tmpFile(chartParam.getChartParam(), ".html");
            WebView wv = new WebView();
            wv.setVisible(false);
            wv.getEngine().load(tmp.toURI().toURL().toExternalForm());
            wv.getEngine().getLoadWorker().stateProperty().addListener((observable, oldValue, newValue) -> {
                wv.getEngine().executeScript("init('" + JSON.toJSONString(data) + "')");
                wv.setVisible(true);
            });
            webView = wv;
            cache.put(key, webView);
        }

        chartParam.getBoard().getChildren().clear();
        chartParam.getBoard().getChildren().add(webView);
    }

    @EventListener
    public void chartTemplateCacheClearEvent(@NonNull ChartTemplateCacheClearEvent event) {
        cache.remove(SecureUtil.md5(event.cacheKey().toString()));
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
