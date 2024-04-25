package org.jxch.capital.client.fx.view;

import cn.hutool.extra.spring.SpringUtil;
import jakarta.annotation.Resource;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jxch.capital.client.db.dto.ParentTemplateConfigDto;
import org.jxch.capital.client.event.PaneTemplateRemoveCacheEvent;
import org.jxch.capital.client.fx.template.dashboard.DashboardParentTemplate;
import org.jxch.capital.client.fx.util.ComboxListCell;
import org.jxch.capital.client.service.NamedOrderedServices;
import org.jxch.capital.client.service.ParentTemplateConfigService;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ResourceBundle;

@Slf4j
@Component
@RequiredArgsConstructor
public class DashboardTemplateView implements Initializable {
    @Resource
    private ParentTemplateConfigService parentTemplateConfigService;

    public ComboBox<DashboardParentTemplate> templateBox;
    public ComboBox<ParentTemplateConfigDto> configBox;
    public TextArea templateParam;
    public TextArea scriptParam;
    public TextField configName;
    public CheckBox auto;
    public StackPane board;

    public void del(ActionEvent actionEvent) {
        parentTemplateConfigService.deleteByConfigName(configName.getText());
    }

    public void add(ActionEvent actionEvent) {
        parentTemplateConfigService.save(ParentTemplateConfigDto.builder()
                .configName(configName.getText())
                .templateName(templateBox.getSelectionModel().getSelectedItem().getName())
                .templateParam(templateParam.getText())
                .scriptParam(scriptParam.getText())
                .build()
        );
        updateConfigBox();
    }

    public void update(ActionEvent actionEvent) {
        parentTemplateConfigService.save(configBox.getSelectionModel().getSelectedItem()
                .setConfigName(configName.getText())
                .setTemplateName(templateBox.getSelectionModel().getSelectedItem().getName())
                .setTemplateParam(templateParam.getText())
                .setScriptParam(scriptParam.getText())
        );
        updateConfigBox();
    }

    public void cacheClear(ActionEvent actionEvent) {
        SpringUtil.publishEvent(new PaneTemplateRemoveCacheEvent(this)
                .setClazz(templateBox.getSelectionModel().getSelectedItem().getClass())
                .setTemplateParam(templateParam.getText())
                .setScriptParam(scriptParam.getText())
        );
    }

    @Async
    public void run(ActionEvent actionEvent) {
        board.getChildren().clear();
        board.getChildren().add(templateBox.getSelectionModel().getSelectedItem().template(templateParam.getText(), scriptParam.getText()));
    }

    private void initParam() {
        templateParam.setText(templateBox.getSelectionModel().getSelectedItem().getTemplateParam());
        scriptParam.setText(templateBox.getSelectionModel().getSelectedItem().getScriptParam());
    }

    private void updateConfigBox() {
        initParam();
        configBox.getItems().clear();
        configBox.getItems().addAll(parentTemplateConfigService.findByTemplateName(templateBox.getSelectionModel().getSelectedItem().getName()));
        configBox.getSelectionModel().selectFirst();
    }

    private void updateParam() {
        templateParam.setText(configBox.getSelectionModel().getSelectedItem().getTemplateParam());
        scriptParam.setText(configBox.getSelectionModel().getSelectedItem().getScriptParam());
        configName.setText(configBox.getSelectionModel().getSelectedItem().getConfigName());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        configBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> updateParam());
        templateBox.getItems().addAll(NamedOrderedServices.allSortedServices(DashboardParentTemplate.class));
        templateBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> updateConfigBox());
        templateBox.getSelectionModel().selectFirst();

        ComboxListCell.setComboxText(templateBox, DashboardParentTemplate::getName);
        ComboxListCell.setComboxText(configBox, ParentTemplateConfigDto::getConfigName);
    }

}
