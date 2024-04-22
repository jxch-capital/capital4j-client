package org.jxch.capital.client.fx.dashboard.impl;

import cn.hutool.cache.CacheUtil;
import cn.hutool.cache.impl.TimedCache;
import cn.hutool.crypto.SecureUtil;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONWriter;
import javafx.scene.web.WebView;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jxch.capital.client.crawler.breath.BreathCrawler;
import org.jxch.capital.client.crawler.dto.BreathParam;
import org.jxch.capital.client.fx.dashboard.ChartTemplate;
import org.jxch.capital.client.fx.dto.ChartParam;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@RequiredArgsConstructor
public class CrawlerWebViewBreathChartTemplate implements ChartTemplate {
    private final TimedCache<String, WebView> cache = CacheUtil.newTimedCache(TimeUnit.HOURS.toMillis(4));
    private final BreathCrawler breathCrawler;

    @Override
    public void chart(ChartParam chartParam, String dataParam) {
        String key = SecureUtil.md5(dataParam);
        WebView webView = cache.get(key);
        if (Objects.isNull(webView)) {
            BreathParam breathParam = Objects.requireNonNull(JSON.parseObject(dataParam, BreathParam.class));
            String htmlTable = breathCrawler.breath(breathParam).htmlTable();
            String content = chartParam.getChartParam().replaceAll("\\$\\{htmlTable}", htmlTable);
            WebView wv = new WebView();
            wv.getEngine().loadContent(content);
            cache.put(key, wv);
            webView = wv;
        }
        chartParam.getBoard().getChildren().clear();
        chartParam.getBoard().getChildren().add(webView);
    }

    @Override
    public String dataParamTemplate() {
        return JSON.toJSONString(new BreathParam(), JSONWriter.Feature.PrettyFormat, JSONWriter.Feature.WriteNulls);
    }

    @Override
    public String chartParamTemplate() {
        return """
                <!DOCTYPE html>
                <html lang="en">
                <head>
                     <meta charset="UTF-8">
                     <style>
                            body {
                                 background-color: #333333;
                            }
                     </style>
                </head>
                <body>
                       ${htmlTable}
                </body>
                """;
    }

    @Override
    public String getName() {
        return "Webview-Crawler-Breath";
    }

}
