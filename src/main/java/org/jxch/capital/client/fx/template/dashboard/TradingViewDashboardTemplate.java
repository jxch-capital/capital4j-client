package org.jxch.capital.client.fx.template.dashboard;

import lombok.extern.slf4j.Slf4j;
import org.jxch.capital.client.fx.template.ParentTemplateService;
import org.springframework.core.io.Resource;

import java.util.Map;

@Slf4j
@ParentTemplateService(scriptParamType = Resource.class, scriptParamValue = "/html/tradingview.html")
public class TradingViewDashboardTemplate implements WebViewDashboardParentTemplate {

    @Override
    public Object data(String templateParam, String scriptParam) {
        return Map.of("symbol", "QQQ");
    }

    @Override
    public String getName() {
        return "TradingView";
    }

    @Override
    public int getOrder() {
        return Integer.MAX_VALUE;
    }

}
