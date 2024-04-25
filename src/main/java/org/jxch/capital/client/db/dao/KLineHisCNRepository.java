package org.jxch.capital.client.db.dao;

import org.jxch.capital.client.db.po.KLineHisCN;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface KLineHisCNRepository extends JpaRepository<KLineHisCN, Long> {

    List<KLineHisCN> findAllByCodeAndDateBetween(Integer code, Date start, Date end);

}
