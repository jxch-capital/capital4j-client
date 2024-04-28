package org.jxch.capital.client.db.dao;

import org.jxch.capital.client.db.po.KLineHisCN5m;
import org.jxch.capital.client.db.sharding.k_line_his_cn_5m.KLineHisCN5mShardingDao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface KLineHisCN5mRepository extends KLineHisCN5mShardingDao<KLineHisCN5m>, JpaRepository<KLineHisCN5m, Long> {


}
