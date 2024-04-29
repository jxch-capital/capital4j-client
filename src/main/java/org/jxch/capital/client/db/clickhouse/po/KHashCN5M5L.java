package org.jxch.capital.client.db.clickhouse.po;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class KHashCN5M5L {
    private Long id;
    private Integer hash5;
    private Long hash10;
    private Long hash16;
    private BigDecimal hash24;
    private BigDecimal hash48;
}
