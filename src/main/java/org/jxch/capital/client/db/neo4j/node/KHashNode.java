package org.jxch.capital.client.db.neo4j.node;

import cn.hutool.core.util.StrUtil;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.neo4j.core.schema.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Node
@Data
@Accessors(chain = true)
public class KHashNode {
    @Id
    @GeneratedValue
    private Long id;
    private String pathSegment;

    @Relationship(type = "CHILD_OF", direction = Relationship.Direction.INCOMING)
    private KHashNode parent;

    @Relationship(type = "CHILD_OF", direction = Relationship.Direction.OUTGOING)
    private List<KHashNode> children = new ArrayList<>();

    @CompositeProperty
    private Map<String, Object> data;

    public KHashNode trim() {
        data.entrySet().forEach(entry -> entry.setValue( StrUtil.trim(entry.getValue().toString(), 0, t -> Objects.equals(t, '\"'))));
        return this;
    }

}
