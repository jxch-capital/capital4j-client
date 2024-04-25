package org.jxch.capital.client.stock.impl;

import lombok.NonNull;
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
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class BSQueryKStockServiceImpl implements StockService {
    private final BSQueryKService bsQueryKService;
    private final BSQueryKMapper bsQueryKMapper;

    @Override
    @Cacheable(value = "BSQueryKStockServiceImpl_query_StockQueryParam", key = "#param.toString()", unless = "#result == null")
    public Map<String, List<KLine>> query(@NonNull StockQueryParam param) {
        return param.getCodes().parallelStream().map(code -> bsQueryKService.queryKLine2Convert(bsQueryKMapper.toBSQueryKParam(param).setCode(code)))
                .collect(Collectors.toMap(kLines -> kLines.getFirst().getCode(), Function.identity()));
    }

}
