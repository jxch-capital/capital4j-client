package org.jxch.capital.client.stock.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class KLine {
    private Date date;
    private String code;
    private Double open;
    private Double high;
    private Double low;
    private Double close;
    private Double volume;
}
