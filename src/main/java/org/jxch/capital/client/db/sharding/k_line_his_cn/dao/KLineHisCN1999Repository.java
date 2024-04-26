package org.jxch.capital.client.db.sharding.k_line_his_cn.dao;

import org.jxch.capital.client.db.sharding.k_line_his_cn.KLineHisCNSharding;
import org.jxch.capital.client.db.sharding.k_line_his_cn.KLineHisCNShardingDao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface KLineHisCN1999Repository extends KLineHisCNShardingDao<KLineHisCNSharding.KLineHisCN1999>, JpaRepository<KLineHisCNSharding.KLineHisCN1999, Long> {
}
