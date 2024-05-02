package org.jxch.capital.client.db.pg.sharding.dao;

import com.google.common.collect.Lists;
import org.jetbrains.annotations.NotNull;
import org.jxch.capital.client.db.pg.sharding.po.KHashCN5M5L;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public interface KHashCN5M5LRepository {

    Integer insert(List<KHashCN5M5L> poList, String tableNumber);

    default Integer insertAll(@NotNull List<KHashCN5M5L> poList) {
        if (!poList.isEmpty()) {
            return poList.stream().collect(Collectors.groupingBy(po -> KHashCN5M5L.getTableNumber(po.getId()))).entrySet().parallelStream()
                    .map(entry -> Lists.partition(entry.getValue().stream().sorted(Comparator.comparing(KHashCN5M5L::getId)).toList(), (int) Math.ceil((double) entry.getValue().size() / 50000)).stream()
                            .map(dtoList -> insert(dtoList.stream().sorted(Comparator.comparing(KHashCN5M5L::getId)).toList(), entry.getKey()))
                            .reduce(Integer::sum).orElseThrow(() -> new RuntimeException("插入失败")))
                    .reduce(Integer::sum).orElseThrow(() -> new RuntimeException("插入失败"));
        }

        return 0;
    }

    List<KHashCN5M5L> findByIdBetween(Long form, Long to, String tableNumber);

    default List<KHashCN5M5L> findByPrefixIdBetween(String prefixId) {
        return findByIdBetween(KHashCN5M5L.getFormIndexByPrefix(prefixId, KHashCN5M5L.LEN_ID),
                KHashCN5M5L.getToIndexByPrefix(prefixId, KHashCN5M5L.LEN_ID),
                KHashCN5M5L.getTableNumber(Long.parseLong(prefixId)));
    }

    List<KHashCN5M5L> findByIdBetweenByPage(Long form, Long to, String tableNumber, int currIndex, int pageSize);

    default List<KHashCN5M5L> findByPrefixIdBetween(String prefixId, int currIndex, int pageSize) {
        return findByIdBetweenByPage(KHashCN5M5L.getFormIndexByPrefix(prefixId, KHashCN5M5L.LEN_ID),
                KHashCN5M5L.getToIndexByPrefix(prefixId, KHashCN5M5L.LEN_ID),
                KHashCN5M5L.getTableNumber(Long.parseLong(prefixId)), currIndex, pageSize);
    }

}
