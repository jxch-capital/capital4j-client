package org.jxch.capital.client.db.clickhouse.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class KHashCN5M5LDto {
    private Long id;
    private Integer hash5;
    private Long hash10;
    private Long hash16;
    private BigDecimal hash24;
    private BigDecimal hash48;
    private String code;
    private String ex;
    private Date date;
}
