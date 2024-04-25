package org.jxch.capital.client.stock;

import lombok.NonNull;
import org.jxch.capital.client.stock.dto.KLine;
import org.jxch.capital.client.stock.dto.StockQueryParam;

import java.util.List;
import java.util.Map;

public interface StockService {

    Map<String, List<KLine>> query(StockQueryParam param);

    default List<KLine> queryOne(StockQueryParam param) {
        return query(param).values().stream().toList().getFirst();
    }

    default Map<String, List<KLine>> query(StockQueryParam param, @NonNull StockService stockService) {
        return stockService.query(param);
    }

}
