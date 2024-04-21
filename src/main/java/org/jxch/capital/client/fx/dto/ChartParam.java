package org.jxch.capital.client.fx.dto;

import javafx.scene.layout.StackPane;
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
public class ChartParam {
    private String chartParam;
    private StackPane board;
}
