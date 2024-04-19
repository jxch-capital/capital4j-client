package org.jxch.capital.client.fx.scene;


import com.jfoenix.assets.JFoenixResources;
import javafx.scene.Parent;
import javafx.scene.Scene;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.jxch.capital.client.config.JFXConfig;
import org.kordamp.bootstrapfx.BootstrapFX;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Arrays;

@Slf4j
@Component
@RequiredArgsConstructor
public class StyleSceneFactory {
    private final ResourcePatternResolver resourcePatternResolver;
    private final JFXConfig jfxConfig;

    public Scene createScene(Parent parent) {
        return init(new Scene(parent));
    }

    @SneakyThrows
    public Scene init(@NonNull Scene scene) {
        scene.getStylesheets().add(JFoenixResources.load("css/jfoenix-fonts.css").toExternalForm());
        scene.getStylesheets().add(JFoenixResources.load("css/jfoenix-design.css").toExternalForm());
        scene.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());

        Arrays.stream(resourcePatternResolver.getResources(jfxConfig.getCssScan())).forEach(resource -> {
            try {
                scene.getStylesheets().add(resource.getURL().toExternalForm());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        return scene;
    }

}
