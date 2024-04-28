package org.jxch.capital.client.khash.rocks;

import org.jxch.capital.client.stock.dto.KLine;

import java.io.File;
import java.util.Date;
import java.util.List;

public interface Files2RocksDB {

    void toRocksDB(List<File> files);

    List<KLine> find(String code, Date date);

}
