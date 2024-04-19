package org.jxch.capital.client.view;

import cn.hutool.extra.spring.SpringUtil;
import javafx.event.ActionEvent;
import javafx.stage.Stage;
import org.jxch.capital.client.Capital4JClientApp;

public class MainView {
    private boolean alwaysOnTop = false;

    public void setAlwaysOnTop(ActionEvent actionEvent) {
        alwaysOnTop = !alwaysOnTop;
        SpringUtil.getBean(Capital4JClientApp.MAIN_STAGE_BEAN_NAME, Stage.class).setAlwaysOnTop(alwaysOnTop);
    }





}
