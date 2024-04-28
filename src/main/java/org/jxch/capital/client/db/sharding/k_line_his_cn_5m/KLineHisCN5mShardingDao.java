package org.jxch.capital.client.db.sharding.k_line_his_cn_5m;

import java.util.Date;
import java.util.List;


public interface KLineHisCN5mShardingDao<T extends KLineHisCN5mSharding> {

    List<T> findAllByCodeAndDateBetween(Integer code, Date start, Date end);

    void deleteByCodeAndDateBetween(Integer code, Date form, Date to);

}
