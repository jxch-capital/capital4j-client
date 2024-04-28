package org.jxch.capital.client.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.extra.spring.SpringUtil;
import jakarta.annotation.PostConstruct;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jxch.capital.client.db.dto.KLineHisCN5mDto;
import org.jxch.capital.client.db.mapper.KLineHisCN5mMapper;
import org.jxch.capital.client.db.sharding.k_line_his_cn_5m.KLineHisCN5mShardingDao;
import org.jxch.capital.client.service.KLineHisCN5mService;
import org.jxch.capital.client.uilt.ReflectionsU;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Slf4j
@Service
@RequiredArgsConstructor
public class KLineHisCN5mServiceImpl implements KLineHisCN5mService {
    private final KLineHisCN5mMapper kLineHisCN5mMapper;
    private List<KLineHisCN5mShardingDao> daoList = new ArrayList<>();
    private List<JpaRepository> jpaList = new ArrayList<>();
    private Integer first = 1999;

    @PostConstruct
    public void init() {
        Map<String, KLineHisCN5mShardingDao> daoMap = SpringUtil.getBeansOfType(KLineHisCN5mShardingDao.class);
        List<String> keyList = daoMap.keySet().stream().sorted().toList();
        for (String key : keyList) {
            daoList.add(daoMap.get(key));
            jpaList.add((JpaRepository) daoMap.get(key));
        }
    }

    @Override
    public List<KLineHisCN5mDto> findByCodeAndDateBetween(Integer code, Date from, Date to) {
        return IntStream.range(Integer.parseInt(DateUtil.format(from, "yyyy")), Integer.parseInt(DateUtil.format(to, "yyyy")) + 1).parallel()
                .mapToObj(year -> kLineHisCN5mMapper.toKLineHisCNDto(daoList.get(year - first).findAllByCodeAndDateBetween(code, from, to)))
                .flatMap(List::stream).toList();
    }

    @Override
    public Integer saveAll(@NonNull List<KLineHisCN5mDto> dtoList) {
        return dtoList.stream().collect(Collectors.groupingBy(kline -> DateUtil.format(kline.getDate(), "yyyy"))).entrySet().stream().parallel().map(entry -> {
            Class<?> poClazz = ReflectionsU.shardingClassForName("KLineHisCN" + entry.getKey());
            List<Object> entieyList = kLineHisCN5mMapper.toEntity(entry.getValue(), poClazz);
            return jpaList.get(Integer.parseInt(entry.getKey()) - first).saveAll(entieyList).size();
        }).reduce(Integer::sum).orElseThrow();
    }

    @Override
    @Transactional
    public void delByCodeAndDateBetween(Integer code, Date from, Date to) {
        IntStream.range(Integer.parseInt(DateUtil.format(from, "yyyy")), Integer.parseInt(DateUtil.format(to, "yyyy")) + 1).parallel()
                .forEach(year -> daoList.get(year - first).deleteByCodeAndDateBetween(code, from, to));
    }

}
