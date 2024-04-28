package org.jxch.capital.client.khash.reader;

import org.jxch.capital.client.stock.dto.KLine;

import java.io.File;
import java.util.List;

public interface KLineFileReader {

    List<KLine> readeAll(File file);

}
