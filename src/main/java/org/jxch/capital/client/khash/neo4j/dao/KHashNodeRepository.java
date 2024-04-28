package org.jxch.capital.client.khash.neo4j.dao;

import org.jxch.capital.client.khash.neo4j.node.KHashNode;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;

import java.util.Optional;

public interface KHashNodeRepository extends Neo4jRepository<KHashNode, Long> {


//    // 正确匹配出节点“4”开始的路径（已知存在）
//    MATCH (n1:KHashNode {pathSegment: "1"})<-[:CHILD_OF]-(n2:KHashNode)<-[:CHILD_OF]-(n3:KHashNode)<-[:CHILD_OF]-(n4:KHashNode {pathSegment: "4"})
//    // 用WITH将节点“4”传递到下一层查询
//    WITH n4
//    // 找到以n4为根的所有叶子节点，这些叶子节点下没有子节点
//    MATCH (n4)-[:CHILD_OF*]->(leaf)
//    WHERE NOT EXISTS ((leaf)-[:CHILD_OF]->())
//    // 返回这些叶子节点
//    RETURN DISTINCT leaf
//    @Query("""
//            MATCH path=(root:KHashNode {pathSegment: $pathSegment})-[:CHILD_OF*]->(leaf)
//                    WHERE NOT (leaf)-[:CHILD_OF]->()
//                    RETURN leaf
//            """)
//    List<KHashNode> findLeafNodesByPathSegment(String pathSegment);

    Optional<KHashNode> findByPathSegmentAndParentId(String pathSegment, Long parentId);

    @Query("MATCH (n) WHERE NOT EXISTS { MATCH (m)-[:CHILD_OF]->(n) } RETURN n")
    Optional<KHashNode> findByPathSegmentAndParentIsNull(String pathSegment);

}
