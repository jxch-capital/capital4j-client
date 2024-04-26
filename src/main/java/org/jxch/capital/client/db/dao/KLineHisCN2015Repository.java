package org.jxch.capital.client.db.dao;

import org.jxch.capital.client.db.po.sharding.KLineHisCNSharding;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface KLineHisCN2015Repository  extends JpaRepository<KLineHisCNSharding.KLineHisCN2015, Long> {
    List<KLineHisCNSharding.KLineHisCN2015> findAllByCodeAndDateBetween(Integer code, Date start, Date end);
}
