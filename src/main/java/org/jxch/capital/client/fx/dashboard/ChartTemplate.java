package org.jxch.capital.client.fx.dashboard;

import org.jxch.capital.client.fx.dto.ChartParam;
import org.jxch.capital.client.service.NamedOrderedService;

@Deprecated
public interface ChartTemplate extends NamedOrderedService {

    void chart(ChartParam chartParam, String dataParam);

    String dataParamTemplate();

    String chartParamTemplate();

}
