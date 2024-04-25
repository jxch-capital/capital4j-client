package org.jxch.capital.client.fx.template.dashboard;

import com.alibaba.fastjson2.JSON;
import javafx.scene.Parent;
import javafx.scene.web.WebView;
import lombok.extern.slf4j.Slf4j;
import org.jxch.capital.client.fx.template.PaneTemplateService;
import org.springframework.core.io.Resource;

import java.util.Map;

@Slf4j
@PaneTemplateService(scriptParamType = Resource.class, scriptParamValue = "/html/tradingview.html")
public class TradingViewDashboardTemplate implements DashboardParentTemplate {

    @Override
    public Parent template(String templateParam, String scriptParam) {
        WebView webView = new WebView();
        webView.setVisible(false);
        webView.getEngine().loadContent(scriptParam);
        webView.getEngine().getLoadWorker().stateProperty().addListener((observable, oldValue, newValue) -> {
            webView.getEngine().executeScript("init('" + JSON.toJSONString(Map.of("symbol", "QQQ")) + "')");
            webView.setVisible(true);
        });

        return webView;
    }

    @Override
    public String getName() {
        return "TradingViewDashboard";
    }

}
