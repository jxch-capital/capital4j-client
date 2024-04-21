package org.jxch.capital.client.python.service.impl;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.resource.ResourceUtil;
import cn.hutool.core.text.csv.CsvUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jxch.capital.client.python.mapper.BSQueryKMapper;
import org.jxch.capital.client.python.binder.BSQueryK2CSV;
import org.jxch.capital.client.python.dto.BSQueryKParam;
import org.jxch.capital.client.python.dto.BSQueryKRes;
import org.jxch.capital.client.python.service.BSQueryKService;
import org.jxch.capital.client.stock.dto.KLine;
import org.jxch.capital.client.stock.dto.StockQueryParam;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class BSQueryKServiceImpl implements BSQueryKService {
    private final BSQueryK2CSV bsQueryK2CSV;
    private final BSQueryKMapper BSQueryKMapper;

    @Override
    public List<BSQueryKRes> queryKLine(BSQueryKParam param) {
        bsQueryK2CSV.run(param);
        try {
            return CsvUtil.getReader().read(ResourceUtil.getUtf8Reader(param.getCsvFile()), BSQueryKRes.class);
        } finally {
            FileUtil.del(param.getCsvFile());
        }
    }

    @Override
    public String downloadKLine(StockQueryParam stockQueryParam) {
        BSQueryKParam param = BSQueryKMapper.toBSQueryKParam(stockQueryParam);
        bsQueryK2CSV.run(param);
        return param.getCsvFile();
    }

    @Override
    public List<KLine> queryKLine2Convert(BSQueryKParam param) {
        return BSQueryKMapper.toKLine(queryKLine(param));
    }

}
