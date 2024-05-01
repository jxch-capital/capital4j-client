package org.jxch.capital.client.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jxch.capital.client.db.pg.dto.KHashCN5M5LDto;
import org.jxch.capital.client.db.pg.mapper.KHashCN5M5LMapper;
import org.jxch.capital.client.db.pg.sharding.dao.KHashCN5M5LRepository;
import org.jxch.capital.client.service.KHashCN5M5LService;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class KHashCN5M5LServiceImpl implements KHashCN5M5LService {
    private final KHashCN5M5LRepository repository;
    private final KHashCN5M5LMapper mapper;


    @Override
    public Integer saveAll(List<KHashCN5M5LDto> dtoList) {
        return repository.insertAll(mapper.toKHashCN5M5L(dtoList));
    }

}
