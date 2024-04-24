package org.jxch.capital.client.fx.scene;

import javafx.scene.Scene;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jxch.capital.client.fx.util.FXBeanUtil;
import org.jxch.capital.client.fx.view.MainView;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class MainScene implements FactoryBean<Scene> {
    private final SceneFactory sceneFactory;

    @Override
    public Scene getObject() {
        return sceneFactory.createScene(FXBeanUtil.getFXBindingBean(MainView.class).getParent());
    }

    @Override
    public Class<?> getObjectType() {
        return Scene.class;
    }

    @Override
    public boolean isSingleton() {
        return false;
    }

}
