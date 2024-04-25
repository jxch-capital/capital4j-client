package org.jxch.capital.client.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jxch.capital.client.db.dao.KLineHisCNRepository;
import org.jxch.capital.client.db.dto.KLineHisCNDto;
import org.jxch.capital.client.db.mapper.KLineHisCNMapper;
import org.jxch.capital.client.service.KLineHisCNService;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class KLineHisCNServiceImpl implements KLineHisCNService {
    private final KLineHisCNRepository kLineHisCNRepository;
    private final KLineHisCNMapper kLineHisCNMapper;


    @Override
    public List<KLineHisCNDto> findByCodeAndDateBetween(Integer code, Date from, Date to) {
        return kLineHisCNMapper.toKLineHisCNDto(kLineHisCNRepository.findAllByCodeAndDateBetween(code, from, to));
    }

    @Override
    public Integer saveAll(List<KLineHisCNDto> dtoList) {
        return kLineHisCNRepository.saveAll(kLineHisCNMapper.toKLineHisCN(dtoList)).size();
    }

}
