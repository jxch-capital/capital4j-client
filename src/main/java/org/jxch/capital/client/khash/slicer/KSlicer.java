package org.jxch.capital.client.khash.slicer;

import org.jxch.capital.client.stock.dto.KLine;

import java.util.List;

public interface KSlicer {

    List<List<KLine>> slicer(List<KLine> kLines);

}
