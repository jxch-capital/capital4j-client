package org.jxch.capital.client.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.extra.spring.SpringUtil;
import jakarta.annotation.PostConstruct;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jxch.capital.client.db.dto.KLineHisCNDto;
import org.jxch.capital.client.db.mapper.KLineHisCNMapper;
import org.jxch.capital.client.db.sharding.k_line_his_cn.KLineHisCNShardingDao;
import org.jxch.capital.client.service.KLineHisCNService;
import org.jxch.capital.client.uilt.ReflectionsU;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Slf4j
@Service
@RequiredArgsConstructor
public class KLineHisCNServiceImpl implements KLineHisCNService {
    private final KLineHisCNMapper kLineHisCNMapper;
    private List<KLineHisCNShardingDao> daoList = new ArrayList<>();
    private List<JpaRepository> jpaList = new ArrayList<>();
    private Integer first = 1999;

    @PostConstruct
    public void init() {
        Map<String, KLineHisCNShardingDao> daoMap = SpringUtil.getBeansOfType(KLineHisCNShardingDao.class);
        List<String> keyList = daoMap.keySet().stream().sorted().toList();
        for (String key : keyList) {
            daoList.add(daoMap.get(key));
            jpaList.add((JpaRepository) daoMap.get(key));
        }
    }

    @Override
    public List<KLineHisCNDto> findByCodeAndDateBetween(Integer code, Date from, Date to) {
        return IntStream.range(Integer.parseInt(DateUtil.format(from, "yyyy")), Integer.parseInt(DateUtil.format(to, "yyyy")) + 1)
                .mapToObj(year -> kLineHisCNMapper.toKLineHisCNDto(daoList.get(year - first).findAllByCodeAndDateBetween(code, from, to)))
                .flatMap(List::stream).toList();
    }

    @Override
    public Integer saveAll(@NonNull List<KLineHisCNDto> dtoList) {
        return dtoList.stream().collect(Collectors.groupingBy(kline -> DateUtil.format(kline.getDate(), "yyyy"))).entrySet().stream().map(entry -> {
            Class<?> poClazz = ReflectionsU.shardingClassForName("KLineHisCN" + entry.getKey());
            List<Object> entieyList = kLineHisCNMapper.toEntity(entry.getValue(), poClazz);
            return jpaList.get(Integer.parseInt(entry.getKey()) - first).saveAll(entieyList).size();
        }).reduce(Integer::sum).orElseThrow();
    }

}
