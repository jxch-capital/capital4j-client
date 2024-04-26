package org.jxch.capital.client.db.sharding.k_line_his_cn.dao;

import org.jxch.capital.client.db.sharding.k_line_his_cn.KLineHisCNSharding;
import org.jxch.capital.client.db.sharding.k_line_his_cn.KLineHisCNShardingDao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface KLineHisCN2025Repository extends KLineHisCNShardingDao<KLineHisCNSharding.KLineHisCN2025>, JpaRepository<KLineHisCNSharding.KLineHisCN2025, Long> {
}
