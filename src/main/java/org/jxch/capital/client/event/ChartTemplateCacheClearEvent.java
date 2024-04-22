package org.jxch.capital.client.event;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEvent;

import java.time.Clock;

@Slf4j
@Getter
@Setter
@Accessors(fluent = true)
public class ChartTemplateCacheClearEvent extends ApplicationEvent {
    private Object cacheKey;

    public ChartTemplateCacheClearEvent(Object source) {
        super(source);
    }

    public ChartTemplateCacheClearEvent(Object source, Clock clock) {
        super(source, clock);
    }
}
