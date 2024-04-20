package org.jxch.capital.client.python.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class BSQueryKRes {
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date date;
    @DateTimeFormat(pattern = "yyyyMMddHHmmssSSS")
    private Date time;
    private String code;
    private Double open;
    private Double high;
    private Double low;
    private Double close;
    private Double volume;
    private Double amount;
    private String turn;
    private String pctChg;
}
