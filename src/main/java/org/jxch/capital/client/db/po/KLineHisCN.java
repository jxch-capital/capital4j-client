package org.jxch.capital.client.db.po;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@Data
@Entity
@EqualsAndHashCode(callSuper = true)
public class KLineHisCN extends BasePo {
    @Column(nullable = false)
    private Integer code;
    @Column(nullable = false)
    private Date date;
    @Column(nullable = false, length = 2)
    private String ex;

    private Double open;
    private Double high;
    private Double low;
    private Double close;
    private Double volume;
}
