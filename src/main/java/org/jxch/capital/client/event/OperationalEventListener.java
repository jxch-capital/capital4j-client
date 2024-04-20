package org.jxch.capital.client.event;

import javafx.geometry.Pos;
import javafx.util.Duration;
import lombok.NonNull;
import org.controlsfx.control.Notifications;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class OperationalEventListener implements ApplicationListener<OperationalEvent> {

    @Override
    public void onApplicationEvent(@NonNull OperationalEvent event) {
        Notifications.create()
                .text(event.getMsg())
                .hideAfter(Duration.seconds(5))
                .position(Pos.TOP_RIGHT)
                .show();
    }

}
