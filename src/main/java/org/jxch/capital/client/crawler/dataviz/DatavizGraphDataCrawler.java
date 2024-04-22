package org.jxch.capital.client.crawler.dataviz;

import com.alibaba.fastjson2.JSONObject;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.jxch.capital.client.crawler.dto.DatavizGraph;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Slf4j
@Component
public class DatavizGraphDataCrawler {
    private final OkHttpClient client = new OkHttpClient.Builder().build();
    private final Request request = new Request.Builder()
            .url("https://production.dataviz.cnn.io/index/fearandgreed/graphdata")
            .addHeader("sec-ch-ua", "\"Google Chrome\";v=\"123\", \"Not:A-Brand\";v=\"8\", \"Chromium\";v=\"123\"")
            .addHeader("sec-ch-ua-mobile", "?0")
            .addHeader("sec-ch-ua-platform", "\"Windows\"")
            .addHeader("Upgrade-Insecure-Requests", "1")
            .addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/123.0.0.0 Safari/537.36")
            .addHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.7")
            .addHeader("Sec-Fetch-Site", "none")
            .addHeader("Sec-Fetch-Mode", "navigate")
            .addHeader("Sec-Fetch-User", "?1")
            .addHeader("Sec-Fetch-Dest", "document")
            .addHeader("host", "production.dataviz.cnn.io")
            .get()
            .build();

    @SneakyThrows
    @Cacheable(value = "DatavizGraphDataCrawler_graphData", unless = "#result == null")
    public DatavizGraph graphData() {
        try (Response response = client.newCall(request).execute()) {
            String jsonString = Objects.requireNonNull(response.body()).string();
            return JSONObject.parseObject(jsonString, DatavizGraph.class);
        }
    }

}
