package org.jxch.capital.client.db.sharding.k_line_his_cn.dao;

import org.jxch.capital.client.db.sharding.k_line_his_cn.KLineHisCNSharding;
import org.jxch.capital.client.db.sharding.k_line_his_cn.KLineHisCNShardingDao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface KLineHisCN2023Repository extends KLineHisCNShardingDao<KLineHisCNSharding.KLineHisCN2023>, JpaRepository<KLineHisCNSharding.KLineHisCN2023, Long> {
}
