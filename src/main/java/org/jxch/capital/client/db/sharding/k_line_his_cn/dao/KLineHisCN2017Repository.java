package org.jxch.capital.client.db.sharding.k_line_his_cn.dao;

import org.jxch.capital.client.db.sharding.k_line_his_cn.KLineHisCNSharding;
import org.jxch.capital.client.db.sharding.k_line_his_cn.KLineHisCNShardingDao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface KLineHisCN2017Repository extends KLineHisCNShardingDao<KLineHisCNSharding.KLineHisCN2017>, JpaRepository<KLineHisCNSharding.KLineHisCN2017, Long> {
}
