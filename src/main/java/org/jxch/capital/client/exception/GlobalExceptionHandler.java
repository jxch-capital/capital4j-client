package org.jxch.capital.client.exception;

import cn.hutool.core.exceptions.ExceptionUtil;
import javafx.geometry.Pos;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

import java.util.Objects;

public class GlobalExceptionHandler {

    public static void setThreadNotificationsExceptionHandler() {
        Thread.setDefaultUncaughtExceptionHandler((thread, throwable) -> {
            throwable.printStackTrace();
            Throwable operationException = ExceptionUtil.getCausedBy(throwable, OperationalException.class);
            if (Objects.nonNull(operationException)) {
                Notifications.create()
                        .title("操作失败")
                        .text(operationException.getMessage())
                        .hideAfter(Duration.seconds(5))
                        .position(Pos.TOP_RIGHT)
                        .showError();
            } else {
                Notifications.create()
                        .title("系统异常")
                        .text(throwable.getMessage())
                        .hideAfter(Duration.seconds(5))
                        .position(Pos.TOP_RIGHT)
                        .showError();
            }
        });
    }

}
