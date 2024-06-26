package org.jxch.capital.client.db.pg.po;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.jxch.capital.client.db.pg.base.IdBasePo;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Accessors(chain = true)
@Entity(name = "parent_template_config")
@Table(indexes = {
        @Index(name = "parent_template_config_config_name", columnList = "config_name"),
        @Index(name = "parent_template_config_template_name", columnList = "template_name"),
})
public class ParentTemplateConfig extends IdBasePo {
    @Column(nullable = false, unique = true)
    private String configName;
    @Column(nullable = false)
    private String templateName;

    @Lob
    @Column(nullable = false)
    private String templateParam;
    @Lob
    @Column(nullable = false)
    private String scriptParam;

}
