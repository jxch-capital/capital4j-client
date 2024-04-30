package org.jxch.capital.client.db.pg.po;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@NoArgsConstructor
@Accessors(chain = true)
@Entity(name = "filter_template_config")
@Table(indexes = {
        @Index(name = "filter_template_config_config_name", columnList = "config_name"),
        @Index(name = "filter_template_config_template_name", columnList = "template_name"),
})
public class FilterTemplateConfig {
    @Column(nullable = false, unique = true)
    private String configName;
    @Column(nullable = false)
    private String templateName;

    @Lob
    @Column(nullable = false)
    private String dataParamTemplate;
    @Lob
    @Column(nullable = false)
    private String chartParamTemplate;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Version
    private Long version;
    private Date createTime;
    private Date updateTime;

    @PreUpdate
    public void onUpdate() {
        this.updateTime = new Date();
    }

    @PrePersist
    public void onCreate() {
        this.createTime = new Date();
        this.updateTime = new Date();
    }

}
