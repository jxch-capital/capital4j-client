package org.jxch.capital.client.fx.component;

import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import lombok.NonNull;
import lombok.Setter;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class TitleBar {
    @Setter
    private Stage stage;
    private boolean alwaysOnTop = false;
    private double xOffset;
    private double yOffset;

    public Label label;

    public void setLabelText(String text) {
        label.setText(text);
    }

    public void setAlwaysOnTop(ActionEvent actionEvent) {
        alwaysOnTop = !alwaysOnTop;
        stage.setAlwaysOnTop(alwaysOnTop);
    }

    public void setIconified(ActionEvent actionEvent) {
        stage.setIconified(true);
    }

    public void setMaximized(ActionEvent actionEvent) {
        stage.setMaximized(!stage.isMaximized());
    }

    public void close(ActionEvent actionEvent) {
        stage.close();
    }

    public void onMouseClicked(@NonNull MouseEvent mouseEvent) {
        if (mouseEvent.getClickCount() > 1) {
            stage.setMaximized(!stage.isMaximized());
        }
    }

    public void onMousePressed(@NonNull MouseEvent mouseEvent) {
        xOffset = stage.getX() - mouseEvent.getScreenX();
        yOffset = stage.getY() - mouseEvent.getScreenY();
    }

    public void onMouseDragged(@NonNull MouseEvent mouseEvent) {
        stage.setX(mouseEvent.getScreenX() + xOffset);
        stage.setY(mouseEvent.getScreenY() + yOffset);
    }

}
