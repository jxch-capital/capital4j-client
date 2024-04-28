package org.jxch.capital.client.khash.redis;

import org.jxch.capital.client.stock.dto.KLine;

import java.io.File;
import java.util.List;

public interface Files2Redis {

    void toRedis(List<File> files);

    List<KLine> find(String key);

}
