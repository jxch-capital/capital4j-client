package org.jxch.capital.client.fx.util;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import lombok.NonNull;
import lombok.SneakyThrows;

import java.io.File;

public class NodeU {

    @SneakyThrows
    public static void loadImage(String imagePath, @NonNull Pane pane) {
        ImageView imageView = new ImageView(new Image(new File(imagePath).toURI().toURL().toExternalForm()));
        pane.getChildren().clear();
        pane.getChildren().add(imageView);
    }

}
