package org.jxch.capital.client.fx.dto;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.jxch.capital.client.python.register.PyParam;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class Py2FileChartParam<T> {
    @PyParam(ignored = true)
    private T param;
    @JSONField(serialize = false)
    private String inputFilePath;
    @JSONField(serialize = false)
    private String outputFilePath;
}
