package org.jxch.capital.client.fx.stage;

import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import lombok.NonNull;
import org.jxch.capital.client.fx.FXBeanUtil;
import org.jxch.capital.client.fx.FXBindingBean;
import org.jxch.capital.client.fx.component.TitleBar;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.stereotype.Component;

@Component
public class TitleBarStage implements FactoryBean<Stage> {

    @Override
    public Stage getObject() {
        return init(new Stage());
    }

    public Stage init(@NonNull Stage stage) {
        stage.initStyle(StageStyle.UNDECORATED);
        FXBindingBean<TitleBar> fxBindingBean = FXBeanUtil.getFXBindingBean(TitleBar.class);
        fxBindingBean.getController().setStage(stage);
        fxBindingBean.getController().setLabelText(stage.getTitle());

        VBox root = new VBox();
        root.getChildren().add(fxBindingBean.getParent());
        root.getChildren().add(stage.getScene().getRoot());
        stage.getScene().setRoot(root);

        return stage;
    }

    @Override
    public Class<?> getObjectType() {
        return Stage.class;
    }

    @Override
    public boolean isSingleton() {
        return false;
    }
}
