package org.jxch.capital.client.db.sharding.k_line_his_cn_5m.dao;

import org.jxch.capital.client.db.sharding.k_line_his_cn_5m.KLineHisCN5mSharding;
import org.jxch.capital.client.db.sharding.k_line_his_cn_5m.KLineHisCN5mShardingDao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface KLineHisCN5m2022Repository extends KLineHisCN5mShardingDao<KLineHisCN5mSharding.KLineHisCN5m2022>, JpaRepository<KLineHisCN5mSharding.KLineHisCN5m2022, Long> {
}
