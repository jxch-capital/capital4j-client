package org.jxch.capital.client.crawler.breath;

import cn.hutool.extra.spring.SpringUtil;
import com.alibaba.fastjson2.JSONObject;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jxch.capital.client.crawler.dto.Breath;
import org.jxch.capital.client.crawler.dto.BreathParam;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Objects;

@Slf4j
@Component
@RequiredArgsConstructor
public class BreathCrawler {
    private final WebClient webClient;

    @SneakyThrows
    @Cacheable(value = "BreathCrawler_breath", unless = "#result == null")
    public Breath breath() {
        return webClient.get().uri("https://www.trading-logic.com/index.html")
                .retrieve().bodyToMono(String.class)
                .map(html -> JSONObject.parseObject(Objects.requireNonNull(Jsoup.parse(Objects.requireNonNull(html))
                        .select("script[data-for=river]").first()).data(), BreathResDto.class).getBreathDto())
                .block();
    }

    public Breath breath(@NonNull BreathParam param) {
        return SpringUtil.getBean(BreathCrawler.class).breath().retained(param.getLength());
    }

}
