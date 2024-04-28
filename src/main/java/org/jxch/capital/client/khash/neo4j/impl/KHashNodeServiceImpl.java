package org.jxch.capital.client.khash.neo4j.impl;

import cn.hutool.core.bean.BeanUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.jxch.capital.client.config.DBConfig;
import org.jxch.capital.client.khash.neo4j.KHashNodeService;
import org.jxch.capital.client.db.neo4j.node.KHashNode;
import org.jxch.capital.client.db.neo4j.node.KHashNodeData;
import org.jxch.capital.client.db.neo4j.dao.KHashNodeRepository;
import org.springframework.data.neo4j.core.Neo4jTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(DBConfig.NEO4J_TRANSACTION_MANAGER)
public class KHashNodeServiceImpl implements KHashNodeService {
    private final Neo4jTemplate neo4jTemplate;
    private final KHashNodeRepository repository;
    private final static String ROOT_PATH = "0";

    @Override
    public KHashNode save(@NotNull List<String> path, KHashNodeData data) {
        path = new ArrayList<>(path);
        path.addFirst(ROOT_PATH);

        Optional<KHashNode> parent = Optional.empty();
        for (String segment : path) {
            final KHashNode currentNode;
            Optional<KHashNode> nodeOpt = parent.isPresent() ?
                    repository.findByPathSegmentAndParentId(segment, parent.get().getId()) :
                    repository.findByPathSegmentAndParentIsNull(segment);

            if (nodeOpt.isPresent()) {
                currentNode = nodeOpt.get();
            } else {
                currentNode = new KHashNode();
                currentNode.setPathSegment(segment);
                currentNode.setParent(parent.orElse(null));
                repository.save(currentNode);
            }
            parent = Optional.of(currentNode);
        }

        parent.ifPresent(node -> {
            node.setData(BeanUtil.beanToMap(data));  // 此处设置数据
            repository.save(node);
        });

        return parent.orElseThrow(() -> new RuntimeException("Unable to create or find path"));
    }

    @Override
    public List<KHashNodeData> get(List<String> path) {
        path = new ArrayList<>(path);
        path.addFirst(ROOT_PATH);

        List<KHashNode> kHashNodes = neo4jTemplate.findAll(createCypherQuery(path), KHashNode.class).stream().map(KHashNode::trim).toList();
        return kHashNodes.stream().map(KHashNode::getData).map(map -> BeanUtil.fillBeanWithMap(map, new KHashNodeData(), true)).toList();
    }

    @NotNull
    private String createCypherQuery(@NotNull List<String> pathSegments) {
        StringBuilder query = new StringBuilder();
//        MATCH (n1:KHashNode {pathSegment: "1"})<-[:CHILD_OF]-(n2:KHashNode {pathSegment: "2"})<-[:CHILD_OF]-(n3:KHashNode {pathSegment: "3"})<-[:CHILD_OF]-(n4:KHashNode {pathSegment: "4"})
//        WITH n4
//        MATCH (n4)-[:CHILD_OF*]->(leaf)
//                WHERE NOT EXISTS ((leaf)-[:CHILD_OF]->())
//        RETURN DISTINCT leaf

        pathSegments = pathSegments.reversed();
        query.append("MATCH ").append(String.format("(n1:KHashNode {pathSegment: \"%s\"})", pathSegments.getFirst()));
        for (int i = 1; i < pathSegments.size(); i++) {
            query.append(String.format("<-[:CHILD_OF]-(n%s:KHashNode {pathSegment: \"%s\"})", i + 1, pathSegments.get(i)));
        }

        query.append("\n").append("WITH n1").append("\n")
                .append("MATCH (n1)-[:CHILD_OF*]->(leaf)").append("\n")
                .append("WHERE NOT EXISTS ((leaf)-[:CHILD_OF]->())").append("\n")
                .append("RETURN DISTINCT leaf");

        return query.toString();
    }

}
