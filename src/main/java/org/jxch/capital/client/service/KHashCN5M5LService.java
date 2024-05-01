package org.jxch.capital.client.service;

import org.jxch.capital.client.db.pg.dto.KHashCN5M5LDto;

import java.util.List;

public interface KHashCN5M5LService {

    Integer saveAll(List<KHashCN5M5LDto> dtoList);

}
