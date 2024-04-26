package org.jxch.capital.client.db.dao;

import org.jxch.capital.client.db.po.KLineHisCN;
import org.jxch.capital.client.db.sharding.k_line_his_cn.KLineHisCNShardingDao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface KLineHisCNRepository extends KLineHisCNShardingDao<KLineHisCN>, JpaRepository<KLineHisCN, Long> {



}
