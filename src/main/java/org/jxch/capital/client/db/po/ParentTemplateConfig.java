package org.jxch.capital.client.db.po;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@NoArgsConstructor
@Accessors(chain = true)
@Entity(name = "parent_template_config")
@Table(indexes = {
        @Index(name = "parent_template_config_config_name", columnList = "config_name"),
        @Index(name = "parent_template_config_template_name", columnList = "template_name"),
})
public class ParentTemplateConfig {
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
