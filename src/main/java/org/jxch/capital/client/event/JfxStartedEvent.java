package org.jxch.capital.client.event;

import org.springframework.context.ApplicationEvent;

import java.time.Clock;

public class JfxStartedEvent extends ApplicationEvent {

    public JfxStartedEvent(Object source) {
        super(source);
    }

    public JfxStartedEvent(Object source, Clock clock) {
        super(source, clock);
    }

}
