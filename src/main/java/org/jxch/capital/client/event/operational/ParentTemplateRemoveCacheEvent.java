package org.jxch.capital.client.event.operational;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.jxch.capital.client.fx.template.ParentTemplate;

import java.time.Clock;

@Setter
@Getter
@Accessors(chain = true)
public class ParentTemplateRemoveCacheEvent extends OperationalEvent {
    private Class<? extends ParentTemplate> clazz;
    private String templateParam;
    private String scriptParam;

    public ParentTemplateRemoveCacheEvent(Object source) {
        super(source);
    }

    public ParentTemplateRemoveCacheEvent(Object source, Clock clock) {
        super(source, clock);
    }

    @Override
    public String getMsg() {
        return "清除缓存";
    }

}
