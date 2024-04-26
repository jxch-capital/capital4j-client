package org.jxch.capital.client.service;

import org.jxch.capital.client.db.dto.KLineHisCNDto;

import java.util.Date;
import java.util.List;

public interface KLineHisCNService {

    List<KLineHisCNDto> findByCodeAndDateBetween(Integer code, Date from, Date to);

    Integer saveAll(List<KLineHisCNDto> dtoList);

    void deleteById(Long id);

    void deleteByIds(List<Long> ids);

}
