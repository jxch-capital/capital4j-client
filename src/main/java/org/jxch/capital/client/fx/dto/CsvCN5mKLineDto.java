package org.jxch.capital.client.fx.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@Builder
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class CsvCN5mKLineDto {
    private Integer code;
    private String ex;

    private Date time;
    private Double open;
    private Double high;
    private Double low;
    private Double close;
    private Double volume;
}
