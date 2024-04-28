package org.jxch.capital.client.khash.neo4j;

import java.util.List;

public interface KHashNodeService {

    KHashNode save(List<String> path, KHashNodeData data);

    List<KHashNodeData> get(List<String> path);

}
