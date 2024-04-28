package org.jxch.capital.client.event.system;

import javafx.geometry.Pos;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class SystemErrorEventListener implements ApplicationListener<SystemErrorEvent> {

    @Override
    public void onApplicationEvent(@NotNull SystemErrorEvent event) {
        Notifications.create()
                .text(event.getErrorMsg())
                .hideAfter(Duration.seconds(10))
                .position(Pos.TOP_RIGHT)
                .showError();
    }

}
