package org.jxch.capital.client.fx.template.dashboard;

import com.alibaba.fastjson2.JSON;
import javafx.scene.Parent;
import javafx.scene.web.WebView;

public interface WebViewDashboardParentTemplate extends DashboardParentTemplate {

    default String html(String templateParam, String scriptParam) {
        return scriptParam;
    }

    default Object data(String templateParam, String scriptParam) {
        return null;
    }

    @Override
    default Parent template(String templateParam, String scriptParam) {
        WebView webView = new WebView();
        webView.setVisible(false);
        webView.getEngine().loadContent(html(templateParam, scriptParam));
        webView.getEngine().getLoadWorker().stateProperty().addListener((observable, oldValue, newValue) -> {
            webView.getEngine().executeScript("init('" + JSON.toJSONString(data(templateParam, scriptParam)) + "')");
            webView.setVisible(true);
        });

        return webView;
    }

}
