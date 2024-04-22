package org.jxch.capital.client.fx.view;

import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.jxch.capital.client.event.PyExecutorPrintEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

@Slf4j
@Component
public class LogView implements Initializable {
    public String allLog;
    public TextArea logger;

    public void addLine(String line) {
        allLog = allLog + line + "\n";
        showLog();
    }

    public void showLog() {
        if (Objects.nonNull(logger)) {
            logger.setText(allLog);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        showLog();
    }

    @EventListener
    public void pyExecutorPrintEvent(@NonNull PyExecutorPrintEvent event) {
        addLine(event.logMsg());
    }

}
