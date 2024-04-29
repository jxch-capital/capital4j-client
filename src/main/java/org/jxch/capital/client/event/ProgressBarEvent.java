package org.jxch.capital.client.event;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.context.ApplicationEvent;

import java.time.Clock;

@Getter
@Setter
@Accessors(chain = true)
public class ProgressBarEvent extends ApplicationEvent {
    private boolean succeed = true;
    private int num = 1;
    private String uuid;
    private String errorMsg;

    public ProgressBarEvent(Object source) {
        super(source);
    }

    public ProgressBarEvent(Object source, Clock clock) {
        super(source, clock);
    }

    public static ProgressBarEvent oneSucceedEvent(Object source, String uuid) {
        return new ProgressBarEvent(source).setNum(1).setSucceed(true).setUuid(uuid);
    }

    public static ProgressBarEvent oneFailEvent(Object source, String uuid, String errorMsg) {
        return new ProgressBarEvent(source).setNum(1).setSucceed(false).setUuid(uuid).setErrorMsg(errorMsg);
    }

}
