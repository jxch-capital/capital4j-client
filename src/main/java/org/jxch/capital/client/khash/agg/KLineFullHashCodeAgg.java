package org.jxch.capital.client.khash.agg;

import org.jxch.capital.client.db.neo4j.node.KHashNodeData;
import org.jxch.capital.client.stock.dto.KLine;

import java.util.List;
import java.util.Map;

public interface KLineFullHashCodeAgg {

    Map<List<String>, KHashNodeData> agg(List<KLine> kLines, String code);

}
