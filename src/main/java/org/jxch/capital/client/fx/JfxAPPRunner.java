package org.jxch.capital.client.fx;

import cn.hutool.extra.spring.SpringUtil;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.jxch.capital.client.event.JfxStartedEvent;
import org.jxch.capital.client.fx.register.FXBeanUtil;
import org.jxch.capital.client.fx.scene.MainScene;
import org.jxch.capital.client.fx.stage.TitleBarStageFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@Profile("!spring-boot-test")
public class JfxAPPRunner extends Application implements CommandLineRunner, ApplicationListener<ContextClosedEvent> {

    @Override
    public void start(@NonNull Stage stage) {
        stage.setScene(FXBeanUtil.getSceneBean(MainScene.class));
        stage.setTitle(SpringUtil.getApplicationName());
        TitleBarStageFactory.init(stage);
        FXBeanUtil.registerStageBean(JfxAPPRunner.class, stage);
        stage.show();

        SpringUtil.getApplicationContext().publishEvent(new JfxStartedEvent(stage));
    }

    @Override
    public void run(String... args) {
        log.info("启动JAVA FX");
        launch(args);
    }

    @Override
    public void stop() {
        log.info("JAVA FX已退出，现在关闭Spring容器");
        ((ConfigurableApplicationContext) SpringUtil.getApplicationContext()).close();
    }

    @Override
    public void onApplicationEvent(@NonNull ContextClosedEvent event) {
        log.info("Spring容器已关闭，现在退出JAVA FX");
        Platform.exit();
    }

}
