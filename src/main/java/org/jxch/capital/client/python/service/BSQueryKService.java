package org.jxch.capital.client.python.service;

import org.jxch.capital.client.python.dto.BSQueryKParam;
import org.jxch.capital.client.python.dto.BSQueryKRes;
import org.jxch.capital.client.stock.dto.KLine;
import org.jxch.capital.client.stock.dto.StockQueryParam;

import java.util.List;

public interface BSQueryKService {

    List<BSQueryKRes> queryKLine(BSQueryKParam param);

    String downloadKLine(StockQueryParam stockQueryParam);


    List<KLine> queryKLine2Convert(BSQueryKParam param);

}
