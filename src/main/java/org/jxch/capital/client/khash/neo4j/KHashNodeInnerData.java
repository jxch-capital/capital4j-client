package org.jxch.capital.client.khash.neo4j;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.jxch.capital.client.stock.dto.KLine;

import java.util.List;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class KHashNodeInnerData extends KHashNodeData {
    private List<KLine> kLines;
}
