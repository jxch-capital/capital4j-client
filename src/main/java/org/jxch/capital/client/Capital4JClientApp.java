package org.jxch.capital.client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.NonNull;
import lombok.SneakyThrows;
import org.jxch.capital.client.config.AppConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.net.URL;
import java.util.Objects;

@SpringBootApplication
public class Capital4JClientApp extends Application {
    private ConfigurableApplicationContext applicationContext;
    private AppConfig appConfig;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void init() {
        applicationContext = SpringApplication.run(Capital4JClientApp.class, getParameters().getRaw().toArray(new String[0]));
        appConfig = applicationContext.getBean(AppConfig.class);
    }

    @Override
    @SneakyThrows
    public void start(@NonNull Stage stage) {
        URL fxml = Objects.requireNonNull(getClass().getResource(appConfig.getFxml()));
        Parent root = FXMLLoader.load(fxml);
        stage.setScene(new Scene(root));
        stage.setTitle(appConfig.getTitle());
        stage.show();
    }

    @Override
    public void stop() {
        applicationContext.close();
    }

}