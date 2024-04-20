package org.jxch.capital.client.python.service.impl;

import cn.hutool.core.io.resource.ResourceUtil;
import cn.hutool.core.text.csv.CsvUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jxch.capital.client.domain.mapper.KLineMapper;
import org.jxch.capital.client.python.binder.BSQueryK2CSV;
import org.jxch.capital.client.python.dto.BSQueryKParam;
import org.jxch.capital.client.python.dto.BSQueryKRes;
import org.jxch.capital.client.python.service.BSQueryKService;
import org.jxch.capital.client.stock.KLine;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class BSQueryKServiceImpl implements BSQueryKService {
    private final BSQueryK2CSV bsQueryK2CSV;
    private final KLineMapper kLineMapper;

    @Override
    public List<BSQueryKRes> queryKLine(BSQueryKParam param) {
        bsQueryK2CSV.run(param);
        return CsvUtil.getReader().read(ResourceUtil.getUtf8Reader(param.getCsvFile()), BSQueryKRes.class);
    }

    @Override
    public List<KLine> queryKLine2Convert(BSQueryKParam param) {
        return kLineMapper.toKLine(queryKLine(param));
    }

}
