package org.jxch.capital.client.scene;

import com.jfoenix.assets.JFoenixResources;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import lombok.RequiredArgsConstructor;
import org.jxch.capital.client.config.AppConfig;
import org.kordamp.bootstrapfx.BootstrapFX;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class MainScene implements FactoryBean<Scene> {
    private final AppConfig appConfig;

    @Override
    public Scene getObject() throws Exception {
        URL fxml = Objects.requireNonNull(getClass().getResource(appConfig.getMainFxml()));
        String style = Objects.requireNonNull(getClass().getResource(appConfig.getMainStyle())).toExternalForm();
        Parent root = FXMLLoader.load(fxml);
        Scene mainScene = new Scene(root);
        mainScene.getStylesheets().add(JFoenixResources.load("css/jfoenix-fonts.css").toExternalForm());
        mainScene.getStylesheets().add(JFoenixResources.load("css/jfoenix-design.css").toExternalForm());
        mainScene.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());
        mainScene.getStylesheets().add(style);
        return mainScene;
    }

    @Override
    public Class<?> getObjectType() {
        return Scene.class;
    }

}
