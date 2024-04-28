package org.jxch.capital.client.khash.neo4j;

import cn.hutool.extra.spring.SpringUtil;
import org.jxch.capital.client.db.neo4j.node.KHashNodeData;

import java.util.ArrayList;
import java.util.List;

public class KHashNodeServices {

    public static void save(List<String> path, String tree, KHashNodeData data) {
        path = new ArrayList<>(path);
        path.addFirst(tree);
        SpringUtil.getBean(KHashNodeService.class).save(path, data);
    }

    public static List<KHashNodeData> get(List<String> path, String tree) {
        path = new ArrayList<>(path);
        path.addFirst(tree);
        return SpringUtil.getBean(KHashNodeService.class).get(path);
    }

}
