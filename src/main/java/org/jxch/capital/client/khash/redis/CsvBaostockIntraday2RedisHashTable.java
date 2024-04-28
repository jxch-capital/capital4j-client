package org.jxch.capital.client.khash.redis;

import cn.hutool.core.date.DateUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.alibaba.fastjson2.JSON;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.jxch.capital.client.config.ThreadConfig;
import org.jxch.capital.client.khash.reader.CsvBaostockKLineFileReader;
import org.jxch.capital.client.stock.dto.KLine;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class CsvBaostockIntraday2RedisHashTable implements Files2Redis {
    public static final String REDIS_KEY = "CsvBaostockIntraday2RedisHashTable";
    private final RedisTemplate<String, String> redisTemplate;
    private final CsvBaostockKLineFileReader csvBaostockKLineFileReader;

    @Override
    public void toRedis(@NotNull List<File> files) {
        files.forEach(file -> SpringUtil.getBean(CsvBaostockIntraday2RedisHashTable.class).toRedis(file));
    }

    public String getHashKey(Date date, @NonNull String code) {
        String[] codes = code.trim().split("\\.");
        return DateUtil.format(date, "yyyyMMdd") + codes[codes.length - 1];
    }

    @Override
    public List<KLine> find(String key) {
        return JSON.parseArray(Objects.requireNonNull(redisTemplate.opsForHash().get(REDIS_KEY, key)).toString(), KLine.class);
    }

    @Async(ThreadConfig.VIRTUAL_THREAD_POOL)
    public void toRedis(File file) {
        List<KLine> kLines = csvBaostockKLineFileReader.readeAll(file);
        kLines.stream().collect(Collectors.groupingBy(kLine -> getHashKey(kLine.getDate(), kLine.getCode()))).forEach((key, value) ->
                redisTemplate.opsForHash().put(REDIS_KEY, key, JSON.toJSONString(value.stream().map(kLine -> kLine.setCode(null)).sorted(Comparator.comparing(KLine::getDate)).toList())));
    }

}
