package org.jxch.capital.client.db.sharding.k_line_his_cn_5m.dao;

import org.jxch.capital.client.db.sharding.k_line_his_cn_5m.KLineHisCN5mSharding;
import org.jxch.capital.client.db.sharding.k_line_his_cn_5m.KLineHisCN5mShardingDao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface KLineHisCN5m2000Repository extends KLineHisCN5mShardingDao<KLineHisCN5mSharding.KLineHisCN5m2000>, JpaRepository<KLineHisCN5mSharding.KLineHisCN5m2000, Long> {
}
