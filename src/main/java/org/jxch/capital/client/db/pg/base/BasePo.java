package org.jxch.capital.client.db.pg.base;

import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Version;
import lombok.Data;

import java.util.Date;

@Data
@MappedSuperclass
public class BasePo {

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
