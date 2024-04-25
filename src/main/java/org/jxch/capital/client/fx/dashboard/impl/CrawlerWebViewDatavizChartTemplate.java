package org.jxch.capital.client.fx.dashboard.impl;

import cn.hutool.cache.CacheUtil;
import cn.hutool.cache.impl.TimedCache;
import cn.hutool.core.io.FileUtil;
import cn.hutool.crypto.SecureUtil;
import com.alibaba.fastjson2.JSON;
import javafx.scene.web.WebView;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.jxch.capital.client.crawler.dataviz.DatavizGraphDataCrawler;
import org.jxch.capital.client.crawler.dto.DatavizGraph;
import org.jxch.capital.client.event.operational.ChartTemplateCacheClearEvent;
import org.jxch.capital.client.fx.dashboard.ChartTemplate;
import org.jxch.capital.client.fx.dto.ChartParam;
import org.springframework.context.event.EventListener;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@RequiredArgsConstructor
public class CrawlerWebViewDatavizChartTemplate implements ChartTemplate {
    private final TimedCache<String, WebView> cache = CacheUtil.newTimedCache(TimeUnit.HOURS.toMillis(5));
    private final ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();
    private final DatavizGraphDataCrawler datavizGraphDataCrawler;


    @Override
    public void chart(@NonNull ChartParam chartParam, String dataParam) {
        String key = SecureUtil.md5(dataParam + chartParam.getChartParam());
        WebView webView = cache.get(key);
        if (Objects.isNull(webView)) {
            DatavizGraph data = datavizGraphDataCrawler.graphData();
            WebView wv = new WebView();
            wv.setVisible(false);
            wv.getEngine().loadContent(chartParam.getChartParam());
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
        return "";
    }

    @Override
    @SneakyThrows
    public String chartParamTemplate() {
        return String.join("\n", FileUtil.readUtf8Lines(
                resourcePatternResolver.getResource("/html/dataviz_fear_greed.html").getFile()));
    }

    @Override
    public String getName() {
        return "WebView-恐慌指数";
    }

}
