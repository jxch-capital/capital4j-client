package org.jxch.capital.client.event.operational;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;

import java.time.Clock;

@Deprecated
@Slf4j
@Getter
@Setter
@Accessors(fluent = true)
public class ChartTemplateCacheClearEvent extends OperationalEvent {
    private Object cacheKey;

    public ChartTemplateCacheClearEvent(Object source) {
        super(source);
    }

    public ChartTemplateCacheClearEvent(Object source, Clock clock) {
        super(source, clock);
    }
}
