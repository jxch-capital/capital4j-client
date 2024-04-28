package org.jxch.capital.client.fx.view;

import cn.hutool.extra.spring.SpringUtil;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import org.jxch.capital.client.db.dto.ParentTemplateConfigDto;
import org.jxch.capital.client.event.operational.ParentTemplateRemoveCacheEvent;
import org.jxch.capital.client.fx.template.ParentTemplate;
import org.jxch.capital.client.fx.util.ComboxListCell;
import org.jxch.capital.client.fx.util.PaneU;
import org.jxch.capital.client.service.NamedOrderedService;
import org.jxch.capital.client.service.NamedOrderedServices;
import org.jxch.capital.client.service.ParentTemplateConfigService;

import java.lang.reflect.ParameterizedType;
import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;
import java.util.ResourceBundle;

public abstract class AbstractTemplateView<T extends ParentTemplate> implements Initializable {
    private ParentTemplateConfigService parentTemplateConfigService;
    public ComboBox<T> templateBox;
    public ComboBox<ParentTemplateConfigDto> configBox;
    public TextArea templateParam;
    public TextArea scriptParam;
    public TextField configName;
    public CheckBox auto;
    public StackPane board;

    public void del() {
        parentTemplateConfigService.deleteByConfigName(configName.getText());
        updateConfigBox();
    }

    public void add() {
        parentTemplateConfigService.save(ParentTemplateConfigDto.builder()
                .configName(configName.getText())
                .templateName(templateBox.getSelectionModel().getSelectedItem().getName())
                .templateParam(templateParam.getText())
                .scriptParam(scriptParam.getText())
                .build()
        );
        updateConfigBox();
    }

    public void update() {
        parentTemplateConfigService.save(configBox.getSelectionModel().getSelectedItem()
                .setConfigName(configName.getText())
                .setTemplateName(templateBox.getSelectionModel().getSelectedItem().getName())
                .setTemplateParam(templateParam.getText())
                .setScriptParam(scriptParam.getText())
        );
        updateConfigBox();
    }

    public void cacheClear() {
        board.getChildren().clear();
        SpringUtil.publishEvent(new ParentTemplateRemoveCacheEvent(this)
                .setClazz(templateBox.getSelectionModel().getSelectedItem().getClass())
                .setTemplateParam(templateParam.getText())
                .setScriptParam(scriptParam.getText())
        );
    }

    public void run() {
        PaneU.updateNode(board, templateBox.getSelectionModel().getSelectedItem().template(templateParam.getText(), scriptParam.getText()));
    }

    @SuppressWarnings("unchecked")
    public Class<T> getGenericTypeClass() {
        ParameterizedType parameterizedType = (ParameterizedType) getClass().getGenericSuperclass();
        return (Class<T>) parameterizedType.getActualTypeArguments()[0];
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        parentTemplateConfigService = SpringUtil.getBean(ParentTemplateConfigService.class);
        configBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> updateParam());

        templateBox.getItems().addAll(NamedOrderedServices.allSortedServices(getGenericTypeClass()));
        templateBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> updateConfigBox());
        templateBox.getSelectionModel().selectFirst();

        ComboxListCell.setComboxText(templateBox, NamedOrderedService::getName);
        ComboxListCell.setComboxText(configBox, ParentTemplateConfigDto::getConfigName);
    }

    private void initParam() {
        templateParam.setText(templateBox.getSelectionModel().getSelectedItem().getTemplateParam());
        scriptParam.setText(templateBox.getSelectionModel().getSelectedItem().getScriptParam());
        configBox.getItems().addAll(new ArrayList<>());
    }

    private void updateConfigBox() {
        initParam();
        configBox.getItems().clear();
        configBox.getItems().addAll(parentTemplateConfigService.findByTemplateName(templateBox.getSelectionModel().getSelectedItem().getName()));
        configBox.getSelectionModel().selectFirst();
    }

    private void updateParam() {
        if (Objects.nonNull(configBox.getSelectionModel().getSelectedItem())) {
            templateParam.setText(configBox.getSelectionModel().getSelectedItem().getTemplateParam());
            scriptParam.setText(configBox.getSelectionModel().getSelectedItem().getScriptParam());
            configName.setText(configBox.getSelectionModel().getSelectedItem().getConfigName());
            if (auto.isSelected()) {
                run();
            }
        }
    }
    
}
