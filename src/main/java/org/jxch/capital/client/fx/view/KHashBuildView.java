package org.jxch.capital.client.fx.view;

import javafx.event.ActionEvent;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class KHashBuildView {
    public ComboBox templateBox;
    public ComboBox configBox;
    public TextArea templateParam;
    public TextArea scriptParam;
    public TextField configName;
    public CheckBox auto;
    public StackPane board;

    public void del(ActionEvent actionEvent) {
    }

    public void add(ActionEvent actionEvent) {
    }

    public void update(ActionEvent actionEvent) {
    }

    public void cacheClear(ActionEvent actionEvent) {
    }

    public void run(ActionEvent actionEvent) {
    }

}
