package org.jxch.capital.client.khash.neo4j.impl;

import lombok.extern.slf4j.Slf4j;
import org.jxch.capital.client.config.DBConfig;
import org.jxch.capital.client.khash.neo4j.KHashNode;
import org.jxch.capital.client.khash.neo4j.KHashNodeData;
import org.jxch.capital.client.khash.neo4j.KHashNodeService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@Transactional(transactionManager = DBConfig.TRANSACTION_MANAGER)
public class KHashNode48ServiceImpl implements KHashNodeService {
    private final KHashNodeService kHashNodeService;
    private final static String ROOT_48 = "48";

    public KHashNode48ServiceImpl(@Qualifier(KHashNodeServiceImpl.BEAN_NAME) KHashNodeService nodeService) {
        this.kHashNodeService = nodeService;
    }

    @Override
    public KHashNode save(List<String> path, KHashNodeData data) {
        path = new ArrayList<>(path);
        path.addFirst(ROOT_48);
        return kHashNodeService.save(path, data);
    }

    @Override
    public List<KHashNodeData> get(List<String> path) {
        path = new ArrayList<>(path);
        path.addFirst(ROOT_48);
        return kHashNodeService.get(path);
    }

}
