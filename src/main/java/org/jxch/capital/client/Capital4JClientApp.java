package org.jxch.capital.client;

import cn.hutool.extra.spring.SpringUtil;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.NonNull;
import lombok.SneakyThrows;
import org.apache.commons.lang3.StringUtils;
import org.jxch.capital.client.scene.MainScene;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class Capital4JClientApp extends Application {
    public final static String MAIN_STAGE_BEAN_NAME = "mainStage";
    private ConfigurableApplicationContext applicationContext;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void init() {
        applicationContext = SpringApplication.run(Capital4JClientApp.class, getParameters().getRaw().toArray(new String[0]));
    }

    @Override
    @SneakyThrows
    public void start(@NonNull Stage stage) {
        stage.setScene(SpringUtil.getBean(StringUtils.uncapitalize(MainScene.class.getSimpleName()), Scene.class));
        stage.setTitle(SpringUtil.getApplicationName());
        SpringUtil.registerBean(MAIN_STAGE_BEAN_NAME, stage);
        stage.show();
    }

    @Override
    public void stop() {
        applicationContext.close();
    }

}