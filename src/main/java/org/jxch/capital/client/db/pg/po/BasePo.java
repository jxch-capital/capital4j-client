package org.jxch.capital.client.db.pg.po;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@MappedSuperclass
public class BasePo {
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
