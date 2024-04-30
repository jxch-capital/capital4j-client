package org.jxch.capital.client.fx.view;

import jakarta.annotation.Resource;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;
import lombok.extern.slf4j.Slf4j;
import org.jxch.capital.client.db.pg.dto.ChartTemplateConfigDto;
import org.jxch.capital.client.event.operational.ChartTemplateCacheClearEvent;
import org.jxch.capital.client.fx.dashboard.ChartTemplate;
import org.jxch.capital.client.fx.dto.ChartParam;
import org.jxch.capital.client.service.ChartTemplateConfigService;
import org.jxch.capital.client.service.NamedOrderedServices;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.*;

@Slf4j
@Component
@Deprecated
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ChartTemplateView implements Initializable {
    public StackPane board;
    public CheckBox autoPlot;
    @Resource
    private ChartTemplateConfigService chartTemplateConfigService;
    @Resource
    private ApplicationEventPublisher publisher;
    private Map<String, ChartTemplate> chartTemplates = new HashMap<>();

    public ComboBox<String> templateServiceBox;
    public ComboBox<String> templateConfigsBox;
    public TextField configName;
    public TextArea dataParamTemplate;
    public TextArea chartParamTemplate;
    private volatile boolean initialized = false;

    private ChartTemplateConfigDto currentConfig;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        refresh();
    }

    private void refresh() {
        initialized = false;
        chartTemplates.clear();
        templateServiceBox.getItems().clear();
        templateConfigsBox.getItems().clear();
        chartTemplates = NamedOrderedServices.allServices(ChartTemplate.class);
        templateConfigsBoxInit();
        templateServiceBoxInit();
        initialized = true;
        templateServiceBox.getSelectionModel().selectFirst();
    }

    private void templateServiceBoxInit() {
        List<String> chartTemplateNames = NamedOrderedServices.allSortedServiceNames(ChartTemplate.class);
        templateServiceBox.getItems().addAll(chartTemplateNames);
        templateServiceBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (initialized) {
                chartParamTemplate.setText(chartTemplates.get(templateServiceBox.getSelectionModel().getSelectedItem()).chartParamTemplate());
                dataParamTemplate.setText(chartTemplates.get(templateServiceBox.getSelectionModel().getSelectedItem()).dataParamTemplate());
                List<String> configNames = chartTemplateConfigService.findByTemplateName(newValue).stream().map(ChartTemplateConfigDto::getConfigName).toList();
                templateConfigsBox.getItems().clear();
                templateConfigsBox.getItems().addAll(configNames);
                templateConfigsBox.getSelectionModel().selectFirst();
            }
        });
    }

    private void templateConfigsBoxInit() {
        templateConfigsBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (initialized) {
                currentConfig = chartTemplateConfigService.findByConfigName(newValue);
                if (Objects.nonNull(currentConfig)) {
                    configName.setText(currentConfig.getConfigName());
                    dataParamTemplate.setText(currentConfig.getDataParamTemplate());
                    chartParamTemplate.setText(currentConfig.getChartParamTemplate());

                    if (autoPlot.isSelected()) {
                        plot();
                    }
                }
            }
        });
    }

    public void save() {
        if (Objects.isNull(currentConfig)) {
            add();
        }
        currentConfig
                .setConfigName(configName.getText())
                .setTemplateName(templateServiceBox.getSelectionModel().getSelectedItem())
                .setDataParamTemplate(dataParamTemplate.getText())
                .setChartParamTemplate(chartParamTemplate.getText());
        currentConfig = chartTemplateConfigService.save(currentConfig);
    }

    public void del(ActionEvent actionEvent) {
        String configName = templateConfigsBox.getSelectionModel().getSelectedItem();

        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("确认删除");
        dialog.setContentText("删除配置：" + configName);
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> response = dialog.showAndWait();
        if (response.isPresent() && response.get() == ButtonType.YES) {
            chartTemplateConfigService.deleteByConfigName(configName);
            refresh();
        }
    }

    public void plot() {
        ChartTemplate chartTemplate = chartTemplates.get(templateServiceBox.getSelectionModel().getSelectedItem());

        chartTemplate.chart(ChartParam.builder()
                .board(board)
                .chartParam(chartParamTemplate.getText())
                .build(), dataParamTemplate.getText());
    }

    public void add() {
        currentConfig = chartTemplateConfigService.save(ChartTemplateConfigDto.builder()
                .configName(configName.getText())
                .templateName(templateServiceBox.getSelectionModel().getSelectedItem())
                .dataParamTemplate(dataParamTemplate.getText())
                .chartParamTemplate(chartParamTemplate.getText())
                .build());

        refresh();
    }

    public void cacheClear(ActionEvent actionEvent) {
        publisher.publishEvent(new ChartTemplateCacheClearEvent(this).cacheKey(chartParamTemplate.getText()));
    }

}
