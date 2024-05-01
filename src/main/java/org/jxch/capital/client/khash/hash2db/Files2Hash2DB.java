package org.jxch.capital.client.khash.hash2db;

import org.jxch.capital.client.service.NamedOrderedService;

import java.io.File;
import java.util.List;

public interface Files2Hash2DB extends NamedOrderedService {

    void toHash2DB(List<File> files);

    void toHash2DB(List<File> files, String uuid);

}
