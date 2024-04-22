package org.jxch.capital.client.crawler.breath;

import cn.hutool.extra.spring.SpringUtil;
import com.alibaba.fastjson2.JSONObject;
import lombok.NonNull;
import lombok.SneakyThrows;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.jsoup.Jsoup;
import org.jxch.capital.client.crawler.dto.Breath;
import org.jxch.capital.client.crawler.dto.BreathParam;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class BreathCrawler {

    @SneakyThrows
    @Cacheable(value = "BreathCrawler_breath", unless = "#result == null")
    public Breath breath() {
        try (Response response = new OkHttpClient.Builder().build().newCall(
                new Request.Builder().url("https://www.trading-logic.com/index.html").get().build()).execute()) {
            String html = Objects.requireNonNull(response.body()).string();
            String jsonString = Objects.requireNonNull(Jsoup.parse(Objects.requireNonNull(html)).select("script[data-for=river]").first()).data();
            return JSONObject.parseObject(jsonString, BreathResDto.class).getBreathDto();
        }
    }

    public Breath breath(@NonNull BreathParam param) {
        return SpringUtil.getBean(BreathCrawler.class).breath().retained(param.getLength());
    }

}
