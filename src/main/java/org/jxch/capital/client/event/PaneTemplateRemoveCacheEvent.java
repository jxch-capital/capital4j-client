package org.jxch.capital.client.event;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.jxch.capital.client.fx.template.ParentTemplate;
import org.springframework.context.ApplicationEvent;

import java.time.Clock;

@Setter
@Getter
@Accessors(chain = true)
public class PaneTemplateRemoveCacheEvent extends ApplicationEvent {
    private Class<? extends ParentTemplate> clazz;
    private String templateParam;
    private String scriptParam;

    public PaneTemplateRemoveCacheEvent(Object source) {
        super(source);
    }

    public PaneTemplateRemoveCacheEvent(Object source, Clock clock) {
        super(source, clock);
    }

}
