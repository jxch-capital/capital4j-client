package org.jxch.capital.client.event.khash;

import lombok.Getter;
import lombok.Setter;
import org.jxch.capital.client.stock.dto.KLine;
import org.springframework.context.ApplicationEvent;

import java.time.Clock;
import java.util.List;

@Setter
@Getter
public class Kline2DBEvent extends ApplicationEvent {
    private List<KLine> kLines;

    public Kline2DBEvent(Object source) {
        super(source);
    }

    public Kline2DBEvent(Object source, Clock clock) {
        super(source, clock);
    }
}
