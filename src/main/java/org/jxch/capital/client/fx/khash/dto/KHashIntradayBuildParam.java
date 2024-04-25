package org.jxch.capital.client.fx.khash.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.jxch.capital.client.stock.dto.StockFrequency;

@Data
@Builder
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class KHashIntradayBuildParam {
    private String csvFilePath;
    private StockFrequency frequency = StockFrequency.MINUTE_5;
    private Integer kNumber = 48;
    private Integer range = 4;
    private Integer avgNumber = 20;

}
