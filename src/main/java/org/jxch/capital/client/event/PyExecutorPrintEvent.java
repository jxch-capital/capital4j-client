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
public class PyExecutorPrintEvent extends ApplicationEvent {
    private String logMsg;

    public PyExecutorPrintEvent(Object source) {
        super(source);
    }

    public PyExecutorPrintEvent(Object source, Clock clock) {
        super(source, clock);
    }

}
