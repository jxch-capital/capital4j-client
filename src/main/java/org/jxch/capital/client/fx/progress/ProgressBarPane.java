package org.jxch.capital.client.fx.progress;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.TimeInterval;
import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.StackPane;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.DecimalFormat;
import java.util.UUID;
import java.util.concurrent.atomic.LongAdder;

public class ProgressBarPane {
    private static final Logger log = LoggerFactory.getLogger(ProgressBarPane.class);
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
    private final TimeInterval timer = DateUtil.timer();

    public ProgressBarPane(Integer total) {
        this.total = total;
        this.pane.getChildren().addAll(this.progressBar, this.label);
    }

    public void add(Integer increment) {
        current.add(increment);

        if (current.intValue() % (total / 100) == 0 || isEnd()) {
            Platform.runLater(() -> {
                progressBar.setProgress(current.doubleValue() / total);
                label.setText(String.format("[%s] == [%s/%s] [%s%%]", timer.intervalPretty(), current.intValue(), total,
                        new DecimalFormat("0.00").format(current.doubleValue() / total)));
            });
        }
    }

    public boolean isEnd() {
        return current.intValue() == total;
    }

}
