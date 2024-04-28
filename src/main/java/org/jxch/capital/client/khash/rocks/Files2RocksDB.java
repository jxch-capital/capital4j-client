package org.jxch.capital.client.khash.rocks;

import org.jxch.capital.client.service.NamedOrderedService;
import org.jxch.capital.client.stock.dto.KLine;

import java.io.File;
import java.util.Date;
import java.util.List;

public interface Files2RocksDB extends NamedOrderedService {

    void toRocksDB(List<File> files);

    void toRocksDB(List<File> files, String uuid);

    List<KLine> find(String code, Date date);

}
