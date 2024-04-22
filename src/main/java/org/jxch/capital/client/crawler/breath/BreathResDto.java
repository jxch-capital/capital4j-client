package org.jxch.capital.client.crawler.breath;

import cn.hutool.core.date.DateUtil;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.jxch.capital.client.crawler.dto.Breath;

import java.util.List;

@Data
@Builder
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class BreathResDto {
    private X x;

    public List<List<String>> getData() {
        return x.getOpts().getSeries().get(0).getData();
    }

    public Breath getBreathDto() {
        Breath breath = new Breath();
        getData().forEach(item -> breath.add(DateUtil.parseDate(item.get(0)).toLocalDateTime().toLocalDate(),
                item.get(2), Integer.valueOf(item.get(1).trim())));
        return breath;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Accessors(chain = true)
    public static class X {
        private Opts opts;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Accessors(chain = true)
    public static class Opts {
        private List<Series> series;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Accessors(chain = true)
    public static class Series {
        private List<List<String>> data;
    }
}
