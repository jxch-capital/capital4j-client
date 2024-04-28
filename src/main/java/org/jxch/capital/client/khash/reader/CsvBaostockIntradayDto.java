package org.jxch.capital.client.khash.reader;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class CsvBaostockIntradayDto {
    private Date time;
    private Double open;
    private Double high;
    private Double low;
    private Double close;
    private Double volume;
    private Double amount;
}
