package org.jxch.capital.client.khash.code;

import org.jxch.capital.client.stock.dto.KLine;

import java.util.List;

public interface KHashCode {

    String hash(List<KLine> kLines);

}
