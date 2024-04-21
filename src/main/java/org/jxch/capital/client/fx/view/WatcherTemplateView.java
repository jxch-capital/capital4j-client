package org.jxch.capital.client.fx.view;

import javafx.event.ActionEvent;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class WatcherTemplateView {

    public ComboBox watcherServiceTemplate;
    public ComboBox watcherConfigTemplate;
    public TextArea watcherConfigParam;
    public TextArea watcherServiceParam;

    public void del(ActionEvent actionEvent) {
    }

    public void add(ActionEvent actionEvent) {
    }

    public void save(ActionEvent actionEvent) {
    }

    public void plot(ActionEvent actionEvent) {

    }
}
