package org.jxch.capital.client.fx.stage;

import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.RequiredArgsConstructor;
import org.jxch.capital.client.fx.register.FXBeanUtil;
import org.jxch.capital.client.fx.register.FXBindingBean;
import org.jxch.capital.client.fx.scene.StyleSceneFactory;
import org.jxch.capital.client.fx.view.LogView;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LogStage {
    private final StyleSceneFactory styleSceneFactory;
    private Stage stage;

    public void init() {
        FXBindingBean<LogView> fxBindingBean = FXBeanUtil.getFXBindingBean(LogView.class);
        Scene scene = styleSceneFactory.createScene(fxBindingBean.getParent());
        stage = new Stage();
        stage.setTitle("系统日志");
        stage.setScene(scene);
        TitleBarStageFactory.init(stage);
    }

    public void show() {
        init();
        stage.show();
    }


}
