package org.jxch.capital.client.khash.neo4j;

import cn.hutool.extra.spring.SpringUtil;
import org.jxch.capital.client.khash.neo4j.node.KHashNodeData;

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

    public static void save48(List<String> path, KHashNodeData data) {
        save(path, "48", data);
    }

    public static List<KHashNodeData> get48(List<String> path) {
        return get(path, "48");
    }

    public static void save24(List<String> path, KHashNodeData data) {
        save(path, "24", data);
    }

    public static List<KHashNodeData> get24(List<String> path) {
        return get(path, "24");
    }

    public static void save16(List<String> path, KHashNodeData data) {
        save(path, "16", data);
    }

    public static List<KHashNodeData> get16(List<String> path) {
        return get(path, "16");
    }

    public static void save10(List<String> path, KHashNodeData data) {
        save(path, "10", data);
    }

    public static List<KHashNodeData> get10(List<String> path) {
        return get(path, "10");
    }

    public static void save5(List<String> path, KHashNodeData data) {
        save(path, "5", data);
    }

    public static List<KHashNodeData> get5(List<String> path) {
        return get(path, "5");
    }

}
