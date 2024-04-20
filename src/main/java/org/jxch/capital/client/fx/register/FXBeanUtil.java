package org.jxch.capital.client.fx.register;

import cn.hutool.extra.spring.SpringUtil;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.NonNull;
import lombok.SneakyThrows;
import org.apache.commons.lang3.StringUtils;
import org.jxch.capital.client.fx.stage.TitleBarStageFactory;

public class FXBeanUtil {

    public static FXMLLoader getFXLoaderBean(@NonNull Class<?> controllerClass) {
        return SpringUtil.getBean(StringUtils.uncapitalize(controllerClass.getSimpleName()) + JFXMLParentRegister.FXML_LOADER_BEAN_NAME_SUFFIX, FXMLLoader.class);
    }

    @SneakyThrows
    public static <T> FXBindingBean<T> getFXBindingBean(@NonNull Class<T> controllerClass) {
        FXMLLoader fxLoaderBean = getFXLoaderBean(controllerClass);
        return FXBindingBean.<T>builder().parent(fxLoaderBean.load()).controller(fxLoaderBean.getController()).build();
    }

    @SneakyThrows
    public static <T> FXBindingBean<T> getFXBindingBean(@NonNull Class<T> controllerClass, T controller) {
        FXMLLoader fxLoaderBean = getFXLoaderBean(controllerClass);
        fxLoaderBean.setController(controller);
        return FXBindingBean.<T>builder().parent(fxLoaderBean.load()).controller(fxLoaderBean.getController()).build();
    }

    public static Scene getSceneBean(@NonNull Class<?> factoryClass) {
        return SpringUtil.getBean(StringUtils.uncapitalize(factoryClass.getSimpleName()), Scene.class);
    }

    @NonNull
    public static Stage createStageAndShow(@NonNull Scene scene, String title) {
        Stage stage = new Stage();
        stage.setTitle(title);
        stage.setScene(scene);
        TitleBarStageFactory.init(stage);
        stage.show();
        return stage;
    }

    public static void registerStageBean(@NonNull Class<?> clazz, Stage stage) {
        SpringUtil.registerBean(StringUtils.uncapitalize(clazz.getSimpleName()) + Stage.class.getSimpleName(), stage);
    }

    public static Stage getStageBean(@NonNull Class<?> clazz) {
        return  SpringUtil.getBean(StringUtils.uncapitalize(clazz.getSimpleName()) + Stage.class.getSimpleName());
    }

}
