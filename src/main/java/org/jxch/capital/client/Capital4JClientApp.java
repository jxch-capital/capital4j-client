package org.jxch.capital.client;

import cn.hutool.extra.spring.SpringUtil;
import javafx.application.Application;
import javafx.stage.Stage;
import lombok.NonNull;
import lombok.SneakyThrows;
import org.jxch.capital.client.fx.FXBeanUtil;
import org.jxch.capital.client.fx.scene.MainScene;
import org.jxch.capital.client.fx.stage.TitleBarStage;
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
        stage.setScene(FXBeanUtil.getSceneBean(MainScene.class));
        stage.setTitle(SpringUtil.getApplicationName());
        stage = SpringUtil.getBean(TitleBarStage.class).init(stage);
        SpringUtil.registerBean(MAIN_STAGE_BEAN_NAME, stage);
        stage.show();
    }

    @Override
    public void stop() {
        applicationContext.close();
    }

}