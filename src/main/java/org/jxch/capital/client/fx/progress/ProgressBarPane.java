package org.jxch.capital.client.fx.progress;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.TimeInterval;
import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextArea;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.text.DecimalFormat;
import java.util.UUID;
import java.util.concurrent.atomic.LongAdder;

@Slf4j
public class ProgressBarPane {
    @Getter
    private final String uuid = UUID.randomUUID().toString();
    @Getter
    private final LongAdder current = new LongAdder();
    @Getter
    private final Integer total;
    @Getter
    private final StackPane pane = new StackPane();
    private final ProgressBar progressBar = new ProgressBar();
    private final Label label = new Label();
    private final TextArea textArea = new TextArea();
    private final TextArea textAreaErrors = new TextArea();
    private final TimeInterval timer = DateUtil.timer();
    @Setter
    @Getter
    private String info = "";
    @Setter
    @Getter
    private String message = "";
    private String errors = "";

    @Setter
    private Integer granularity = 100;

    public ProgressBarPane(Integer total) {
        this.total = total;
        this.label.setText(msg());
        progressBar.setMinWidth(textArea.getWidth());

        VBox vBox = new VBox();
        vBox.getChildren().addAll(this.progressBar, this.label, new Label("Detail"), this.textArea, new Label("Error"), this.textAreaErrors);
        this.pane.getChildren().add(vBox);
    }

    public void add(Integer increment) {
        current.add(increment);
        log.info("{} {}", msg(), info);

        if (current.intValue() % (total / granularity) == 0 || isEnd()) {
            Platform.runLater(() -> {
                progressBar.setProgress(current.doubleValue() / total);
                label.setText(String.format("%s %s", msg(), info));
                textArea.setText(message);
                textAreaErrors.setText(errors);
            });
        }
    }

    private String msg() {
        return String.format("[%s] [%s/%s] [%s%%] [%s]", uuid, current.intValue(), total,
                new DecimalFormat("0.00").format(current.doubleValue() / total * 100), timer.intervalPretty());
    }

    public boolean isEnd() {
        return current.intValue() == total;
    }

    public void addMessageLine(String message) {
        this.message += "\n" + message;
    }

    public void addErrorsLine(String errors) {
        this.errors += "\n" + errors;
        Platform.runLater(() -> textAreaErrors.setText(this.errors));
    }

}
