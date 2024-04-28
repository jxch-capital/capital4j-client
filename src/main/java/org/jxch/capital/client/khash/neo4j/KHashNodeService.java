package org.jxch.capital.client.khash.neo4j;

import org.jxch.capital.client.db.neo4j.node.KHashNode;
import org.jxch.capital.client.db.neo4j.node.KHashNodeData;

import java.util.List;

public interface KHashNodeService {

    KHashNode save(List<String> path, KHashNodeData data);

    List<KHashNodeData> get(List<String> path);

}
