package org.jxch.capital.client.stock.dto;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson2.JSON;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class StockQueryParam {
    private List<String> codes = List.of();
    @Builder.Default
    private Date startDate = DateUtil.date().offset(DateField.DAY_OF_YEAR, -20);
    @Builder.Default
    private Date endDate = DateUtil.date();
    @Builder.Default
    private StockFrequency frequency = StockFrequency.MINUTE_5;

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

}
