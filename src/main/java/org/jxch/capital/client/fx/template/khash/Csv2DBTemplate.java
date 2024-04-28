package org.jxch.capital.client.fx.template.khash;

import cn.hutool.core.io.resource.ResourceUtil;
import cn.hutool.core.text.csv.CsvReader;
import cn.hutool.core.text.csv.CsvUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.alibaba.fastjson2.JSON;
import javafx.scene.Parent;
import javafx.scene.control.ProgressBar;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jxch.capital.client.config.ThreadConfig;
import org.jxch.capital.client.fx.dto.Csv5m2DBParam;
import org.jxch.capital.client.fx.dto.CsvCN5mKLineDto;
import org.jxch.capital.client.fx.template.ParentTemplateService;
import org.springframework.scheduling.annotation.Async;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@RequiredArgsConstructor
@ParentTemplateService(templateParamType = Csv5m2DBParam.class, cache = false)
public class Csv2DBTemplate implements KHashParentTemplate {

    @Override
    public Parent template(String templateParam, String scriptParam) {
        Csv5m2DBParam param = JSON.parseObject(templateParam, Csv5m2DBParam.class);
        File[] files = Objects.requireNonNull(new File(param.getCsvPath()).listFiles());
        ProgressBar progressBar = new ProgressBar(files.length);
        SpringUtil.getBean(Csv2DBTemplate.class).progress(files, progressBar);
        return progressBar;
    }

    @Async(value = ThreadConfig.VIRTUAL_THREAD_POOL)
    public void progress(File[] files, ProgressBar progressBar) {
        AtomicInteger atomicInteger = new AtomicInteger();
        Arrays.stream(files).parallel().forEach(file -> {
            final CsvReader reader = CsvUtil.getReader();
            List<CsvCN5mKLineDto> result = reader.read(ResourceUtil.getUtf8Reader(file.getAbsolutePath()), CsvCN5mKLineDto.class);
            SpringUtil.getBean(Csv2DBTemplate.class).toDB(result);
            progressBar.setProgress(atomicInteger.incrementAndGet());
        });
    }

    @Async(value = ThreadConfig.VIRTUAL_THREAD_POOL)
    public void toDB(List<CsvCN5mKLineDto> csvCN5MKLineDtoList) {
    }

    @Override
    public String getName() {
        return "CSV-2DB";
    }

}
