package org.jxch.capital.client.khash.tree;

import org.jxch.capital.client.service.NamedOrderedService;

import java.io.File;
import java.util.List;

public interface Files2HashTree extends NamedOrderedService {

    void toHashTree(List<File> files);

    void toHashTree(List<File> files, String uuid);

}
