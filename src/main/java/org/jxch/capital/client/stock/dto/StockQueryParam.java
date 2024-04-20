package org.jxch.capital.client.stock.dto;

import cn.hutool.core.date.DateUtil;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class StockQueryParam {
    private String code;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date startDate;
    @Builder.Default
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endDate = DateUtil.date();
    @Builder.Default
    private StockFrequency frequency = StockFrequency.MINUTE_5;
}
