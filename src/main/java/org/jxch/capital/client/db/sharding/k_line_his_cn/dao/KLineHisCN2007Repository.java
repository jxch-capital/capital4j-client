package org.jxch.capital.client.db.sharding.k_line_his_cn.dao;

import org.jxch.capital.client.db.sharding.k_line_his_cn.KLineHisCNSharding;
import org.jxch.capital.client.db.sharding.k_line_his_cn.KLineHisCNShardingDao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface KLineHisCN2007Repository extends KLineHisCNShardingDao<KLineHisCNSharding.KLineHisCN2007>, JpaRepository<KLineHisCNSharding.KLineHisCN2007, Long> {
}
