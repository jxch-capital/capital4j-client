package org.jxch.capital.client.fx.view;

import javafx.event.ActionEvent;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.layout.StackPane;
import lombok.extern.slf4j.Slf4j;
import org.jxch.capital.client.fx.filter.FilterTemplate;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class FilterTemplateView {
    public ComboBox<FilterTemplate> filterService;
    public ComboBox<String> filterConfig;
    public TextArea filterServiceParam;
    public TextArea filterConfigParam;
    public StackPane filterPane;

    public void add(ActionEvent actionEvent) {
    }

    public void del(ActionEvent actionEvent) {
    }

    public void save(ActionEvent actionEvent) {
    }

    public void filter(ActionEvent actionEvent) {
    }
}
