package org.jxch.capital.client.event.system;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.context.ApplicationEvent;

import java.time.Clock;

@Getter
@Setter
@Accessors(chain = true)
public class SystemErrorEvent extends ApplicationEvent {
    private String errorMsg;


    public SystemErrorEvent(Object source) {
        super(source);
    }

    public SystemErrorEvent(Object source, Clock clock) {
        super(source, clock);
    }
}
