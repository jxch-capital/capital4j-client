package org.jxch.capital.client.fx.view;

import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import lombok.extern.slf4j.Slf4j;
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

}
