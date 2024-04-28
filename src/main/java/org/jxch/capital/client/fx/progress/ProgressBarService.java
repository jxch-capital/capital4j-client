package org.jxch.capital.client.fx.progress;

import cn.hutool.extra.spring.SpringUtil;
import javafx.application.Platform;
import javafx.scene.control.ProgressBar;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.jxch.capital.client.event.ProgressBarEvent;
import org.jxch.capital.client.event.system.SystemErrorEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Service
public class ProgressBarService implements Progress, ApplicationListener<ProgressBarEvent> {
    private static final Map<String, ProgressBarPane> PROGRESSES = new ConcurrentHashMap<>();

    @Override
    public ProgressBarPane registerProgress(@NotNull Integer total) {
        ProgressBarPane progressBarPane = new ProgressBarPane(total);
        PROGRESSES.putIfAbsent(progressBarPane.getUuid(), progressBarPane);
        return progressBarPane;
    }

    @Override
    public void onApplicationEvent(@NotNull ProgressBarEvent event) {
        ProgressBarPane pane = PROGRESSES.get(event.getUuid());
        pane.add(event.getNum());
        log.info("[{}]: [{}/{}]", event.getUuid(), pane.getCurrent().intValue(), pane.getTotal());

        if (!event.isSucceed()) {
            SpringUtil.publishEvent(new SystemErrorEvent(this).setErrorMsg(event.getErrorMsg()));
        }
        if (pane.isEnd()) {
            PROGRESSES.remove(pane.getUuid());
        }
    }

}
