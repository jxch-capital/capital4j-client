package org.jxch.capital.client.event.life;

import org.springframework.context.ApplicationEvent;

import java.time.Clock;

public class JfxWarmedEvent extends ApplicationEvent {
    public JfxWarmedEvent(Object source) {
        super(source);
    }

    public JfxWarmedEvent(Object source, Clock clock) {
        super(source, clock);
    }
}
