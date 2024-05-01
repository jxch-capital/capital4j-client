package org.jxch.capital.client.fx.progress;

import cn.hutool.extra.spring.SpringUtil;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.jxch.capital.client.event.ProgressBarEvent;
import org.jxch.capital.client.event.system.SystemErrorEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
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

    public ProgressBarPane registerProgress(@NotNull Integer total, int granularity) {
        ProgressBarPane pane = registerProgress(total);
        pane.setGranularity(granularity);
        return pane;
    }

    @Async
    @Override
    public void onApplicationEvent(@NotNull ProgressBarEvent event) {
        if (!PROGRESSES.containsKey(event.getUuid())) {
            return;
        }

        ProgressBarPane pane = PROGRESSES.get(event.getUuid());

        if (event.isFail()) {
            SpringUtil.publishEvent(new SystemErrorEvent(this).setErrorMsg(event.getErrorMsg()));
            pane.setInfo(event.getInfo());
            pane.addMessageLine(event.getErrorMsg());
            pane.addMessageLine("详情请查看日志");
            pane.addErrorsLine(event.getInfo() + "\n" + event.getErrorMsg());
        }

        if (event.hasInfo()) {
            pane.setInfo(event.getInfo());
            pane.addMessageLine(event.getInfo());
        }

        if (pane.isEnd()) {
            PROGRESSES.remove(pane.getUuid());
        }

        pane.add(event.getNum());
    }

}
