package org.jxch.capital.client.fx;

import cn.hutool.extra.spring.SpringUtil;
import javafx.application.Application;
import javafx.stage.Stage;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.jxch.capital.client.fx.register.FXBeanUtil;
import org.jxch.capital.client.fx.scene.MainScene;
import org.jxch.capital.client.fx.stage.TitleBarStageFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class JfxAPPLifecycle extends Application implements CommandLineRunner {

    @Override
    public void stop() {
        log.info("关闭Spring容器");
        ((ConfigurableApplicationContext) SpringUtil.getApplicationContext()).close();
    }

    @Override
    public void start(@NonNull Stage stage) {
        stage.setScene(FXBeanUtil.getSceneBean(MainScene.class));
        stage.setTitle(SpringUtil.getApplicationName());
        TitleBarStageFactory.init(stage);
        FXBeanUtil.registerStageBean(JfxAPPLifecycle.class, stage);
        stage.show();
    }

    @Override
    public void run(String... args) {
        log.info("启动JAVA FX");
        launch(args);
    }

}
