package org.jxch.capital.client.stock.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jxch.capital.client.python.mapper.BSQueryKMapper;
import org.jxch.capital.client.python.service.BSQueryKService;
import org.jxch.capital.client.stock.StockService;
import org.jxch.capital.client.stock.dto.KLine;
import org.jxch.capital.client.stock.dto.StockQueryParam;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class BSQueryKStockServiceImpl implements StockService {
    private final BSQueryKService bsQueryKService;
    private final BSQueryKMapper bsQueryKMapper;

    @Override
    @Cacheable(value = "BSQueryKStockServiceImpl_query_StockQueryParam", key = "#param.toString()", unless = "#result == null")
    public List<KLine> query(StockQueryParam param) {
        return bsQueryKService.queryKLine2Convert(bsQueryKMapper.toBSQueryKParam(param));
    }

}
