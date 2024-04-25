package org.jxch.capital.client.fx.util;

import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import lombok.NonNull;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.util.function.Supplier;

@Slf4j
public class PaneU {

    @SneakyThrows
    public static void loadImage(String imagePath, @NonNull Pane pane) {
        ImageView imageView = new ImageView(new Image(new File(imagePath).toURI().toURL().toExternalForm()));
        pane.getChildren().clear();
        pane.getChildren().add(imageView);
    }

    public static void updateNode(@NonNull Pane pane, @NonNull Node node) {
        pane.getChildren().clear();
        pane.getChildren().add(node);
    }

    public static void updateNode(@NonNull Pane pane, @NonNull Supplier<Node> supplier) {
        Platform.runLater(() -> {
            pane.getChildren().clear();
            pane.getChildren().add(new Button("正在加载"));
            pane.layout();
        });
        Platform.runLater(() -> {
            pane.getChildren().clear();
            pane.getChildren().add(supplier.get());
        });
    }


}
