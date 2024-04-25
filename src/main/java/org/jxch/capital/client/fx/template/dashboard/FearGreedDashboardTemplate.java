package org.jxch.capital.client.fx.template.dashboard;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jxch.capital.client.crawler.dataviz.DatavizGraphDataCrawler;
import org.jxch.capital.client.fx.template.ParentTemplateService;
import org.springframework.core.io.Resource;

@Slf4j
@RequiredArgsConstructor
@ParentTemplateService(scriptParamType = Resource.class, scriptParamValue = "/html/dataviz_fear_greed.html")
public class FearGreedDashboardTemplate implements WebViewDashboardParentTemplate {
    private final DatavizGraphDataCrawler datavizGraphDataCrawler;

    @Override
    public Object data(String templateParam, String scriptParam) {
        return datavizGraphDataCrawler.graphData();
    }

    @Override
    public String getName() {
        return "恐慌指数";
    }

}
