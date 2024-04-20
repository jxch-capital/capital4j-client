package org.jxch.capital.client.python.service;

import org.jxch.capital.client.python.dto.BSQueryKParam;
import org.jxch.capital.client.python.dto.BSQueryKRes;
import org.jxch.capital.client.stock.KLine;

import java.util.List;

public interface BSQueryKService {

    List<BSQueryKRes> queryKLine(BSQueryKParam param);

    List<KLine> queryKLine2Convert(BSQueryKParam param);

}
