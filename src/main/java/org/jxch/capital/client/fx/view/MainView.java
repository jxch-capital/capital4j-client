package org.jxch.capital.client.fx.view;

import jakarta.annotation.Resource;
import javafx.event.ActionEvent;
import javafx.scene.layout.StackPane;
import org.jxch.capital.client.fx.register.FXBeanUtil;
import org.jxch.capital.client.fx.stage.LogStage;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class MainView {
    @Resource
    private LogStage logStage;
    public StackPane stackPane;

    public void envSettings(ActionEvent actionEvent) {
        stackPane.getChildren().clear();
        stackPane.getChildren().add(FXBeanUtil.getFXBindingBean(EnvSettingsView.class).getParent());
    }

    public void systemLog(ActionEvent actionEvent) {
        logStage.show();
    }

    public void template(ActionEvent actionEvent) {
        stackPane.getChildren().clear();
        stackPane.getChildren().add(FXBeanUtil.getFXBindingBean(ChartTemplateView.class).getParent());
    }

    public void watcherTemplate(ActionEvent actionEvent) {
        stackPane.getChildren().clear();
        stackPane.getChildren().add(FXBeanUtil.getFXBindingBean(WatcherTemplateView.class).getParent());
    }

    public void dbManagement(ActionEvent actionEvent) {

    }

    public void filterTemplate(ActionEvent actionEvent) {
        stackPane.getChildren().clear();
        stackPane.getChildren().add(FXBeanUtil.getFXBindingBean(FilterTemplateView.class).getParent());
    }
}
