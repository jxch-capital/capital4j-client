package org.jxch.capital.client.fx.stage;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jxch.capital.client.fx.os.FX2OS;
import org.jxch.capital.client.fx.scene.SceneFactory;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class StageFactory {
    private final SceneFactory sceneFactory;
    private final FX2OS fx2OS;

    public Stage createStage() {
        return new Stage();
    }

    public Stage createStage(Scene scene, String title) {
        Stage stage = new Stage();
        stage.setTitle(title);
        stage.setScene(scene);
        return stage;
    }

    public Stage createStage(Class<?> viewClass, String title) {
        return createStage(sceneFactory.createScene(viewClass), title);
    }

    public Stage createStage(Parent parent, String title) {
        return createStage(sceneFactory.createScene(parent), title);
    }

    public void show(@NonNull Stage stage) {
        stage.show();
        fx2OS.setDarkMode(stage, true);
    }

    public void show(Class<?> viewClass, String title) {
        show(createStage(viewClass, title));
    }

    public void show(Parent parent, String title) {
        show(createStage(parent, title));
    }

    public void show(Scene scene, String title) {
        show(createStage(scene, title));
    }

}
