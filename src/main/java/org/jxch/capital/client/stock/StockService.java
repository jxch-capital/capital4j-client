package org.jxch.capital.client.stock;

import lombok.NonNull;
import org.jxch.capital.client.stock.dto.KLine;
import org.jxch.capital.client.stock.dto.StockQueryParam;

import java.util.List;

public interface StockService {

    List<KLine> query(StockQueryParam param);

    default List<KLine> query(StockQueryParam param, @NonNull StockService stockService) {
        return stockService.query(param);
    }

}
