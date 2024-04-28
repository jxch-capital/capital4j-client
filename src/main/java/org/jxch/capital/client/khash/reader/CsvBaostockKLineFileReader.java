package org.jxch.capital.client.khash.reader;

import cn.hutool.core.io.resource.ResourceUtil;
import cn.hutool.core.text.csv.CsvUtil;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jxch.capital.client.khash.mapper.KHashMapper;
import org.jxch.capital.client.stock.dto.KLine;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class CsvBaostockKLineFileReader implements KLineFileReader {
    private final KHashMapper kHashMapper;

    @Override
    public List<KLine> readeAll(@NonNull File file) {
        return kHashMapper.toKLineByCsvBaostockIntraday(CsvUtil.getReader().read(
                ResourceUtil.getUtf8Reader(file.getAbsolutePath()), CsvBaostockIntradayDto.class));
    }

}
