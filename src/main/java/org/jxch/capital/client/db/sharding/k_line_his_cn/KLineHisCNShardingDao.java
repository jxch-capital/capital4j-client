package org.jxch.capital.client.db.sharding.k_line_his_cn;

import java.util.Date;
import java.util.List;

public interface KLineHisCNShardingDao<T extends KLineHisCNSharding> {

    List<T> findAllByCodeAndDateBetween(Integer code, Date start, Date end);

}
