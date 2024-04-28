package org.jxch.capital.client.fx.progress;

import javafx.application.Platform;
import javafx.scene.control.ProgressBar;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.UUID;
import java.util.concurrent.atomic.LongAdder;

@Data
@Accessors(chain = true)
@NoArgsConstructor
public class ProgressBarDto {
    private String uuid = UUID.randomUUID().toString();
    private LongAdder current = new LongAdder();
    private Integer total;
    private ProgressBar progressBar;

    public ProgressBarDto(Integer total) {
        this.total = total;
    }

    public ProgressBarDto(String uuid, Integer total) {
        this.uuid = uuid;
        this.total = total;
    }


    public ProgressBarDto add(Integer increment) {
        current.add(increment);

        if (current.intValue() % (total / 100) == 0 || isEnd()) {
            Platform.runLater(() -> progressBar.setProgress(current.doubleValue() / total));
        }

        return this;
    }

    public boolean isEnd() {
        return current.intValue() == total;
    }

}
