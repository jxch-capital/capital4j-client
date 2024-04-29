package org.jxch.capital.client.khash.agg;

import org.jxch.capital.client.stock.dto.KLine;

import java.util.List;

public interface KLines2KHash2Agg<T> {

    List<T> agg(List<KLine> kLines, String code, String ex);

}
