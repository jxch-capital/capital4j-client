package org.jxch.capital.client.fx.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Builder
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class Csv5m2DBParam {
    @Builder.Default
    private String csvPath = "G:\\app\\backup\\data\\stock_data\\csv\\5-2";
}
