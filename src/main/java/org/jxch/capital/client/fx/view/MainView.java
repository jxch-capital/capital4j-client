package org.jxch.capital.client.fx.view;

import javafx.event.ActionEvent;
import javafx.scene.layout.StackPane;
import lombok.RequiredArgsConstructor;
import org.jxch.capital.client.fx.util.FXBeanUtil;
import org.jxch.capital.client.fx.stage.StageFactory;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@RequiredArgsConstructor
public class MainView {
    private final StageFactory stageFactory;
    public StackPane stackPane;

    public void envSettings(ActionEvent actionEvent) {
        stackPane.getChildren().clear();
        stackPane.getChildren().add(FXBeanUtil.getFXBindingBean(EnvSettingsView.class).getParent());
    }

    public void systemLog(ActionEvent actionEvent) {
        stageFactory.show(LogView.class, "系统日志");
    }

    @Deprecated
    public void template_bac(ActionEvent actionEvent) {
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

    public void dashboardTemplate(ActionEvent actionEvent) {
        stackPane.getChildren().clear();
        stackPane.getChildren().add(FXBeanUtil.getFXBindingBean(DashboardTemplateView.class).getParent());

    }
}
