package org.jxch.capital.client.service;

import org.jxch.capital.client.db.dto.KLineHisCN5mDto;

import java.util.Date;
import java.util.List;

public interface KLineHisCN5mService {

    List<KLineHisCN5mDto> findByCodeAndDateBetween(Integer code, Date from, Date to);

    Integer saveAll(List<KLineHisCN5mDto> dtoList);

    void delByCodeAndDateBetween(Integer code, Date from, Date to);

}
