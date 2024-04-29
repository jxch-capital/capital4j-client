package org.jxch.capital.client.khash.agg;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;
import org.jxch.capital.client.khash.code.GridNumKHashCode;
import org.jxch.capital.client.db.neo4j.node.KHashNodeData;
import org.jxch.capital.client.khash.slicer.DailyKSlicer;
import org.jxch.capital.client.stock.dto.KLine;

import java.util.*;

@Data
@Slf4j
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class DailyKLineFullHashCodeAgg implements KLineFullHashCodeAgg {
    @Builder.Default
    private DailyKSlicer dailyKSlicer = new DailyKSlicer();
    @Builder.Default
    private Integer dailyKNum = 48;
    @Builder.Default
    private Integer hisUpLength = 20;
    @Builder.Default
    private Integer hisDownLength = 20;
    @Builder.Default
    private Integer ranger = 4;
    @Builder.Default
    private Boolean dailyKumValidate = true;

    @Override
    public Map<List<String>, KHashNodeData> agg(List<KLine> kLines, String code) {
        Map<List<String>, KHashNodeData> agg = new HashMap<>();
        KLine referenceLine = null;
        int hisUp = 0, hisDown = 0;
        double fluctuationDownAvg = 0, fluctuationUpAvg = 0;
        double[] ups = new double[hisUpLength];
        double[] downs = new double[hisDownLength];

        List<List<KLine>> sliceK = dailyKSlicer.slicer(kLines).stream().filter(kl -> !dailyKumValidate || kl.size() == dailyKNum)
                .filter(Objects::nonNull).sorted(Comparator.comparing(list -> list.getFirst().getDate())).toList();

        if (sliceK.size() < hisUpLength + hisDownLength) {
            log.warn("不符合聚合操纵的前置条件，必须给定前{}天的前置数据", hisUpLength + hisDownLength);
            return agg;
        }

        for (List<KLine> dayK : sliceK) {
            if (hisUp > hisUpLength && hisDown > hisDownLength) {
                GridNumKHashCode gridNumKHash = GridNumKHashCode.builder()
                        .ranger(ranger)
                        .referenceLine(referenceLine)
                        .fluctuationDown(fluctuationDownAvg)
                        .fluctuationUp(fluctuationUpAvg)
                        .build();
                List<String> fullHashCodeArr = gridNumKHash.fullHashCodeArr(dayK);
                agg.put(fullHashCodeArr, new KHashNodeData().setDate(dayK.getFirst().getDate()).setCode(code));
            }

            referenceLine = dayK.getLast();
            double high = dayK.stream().mapToDouble(KLine::getHigh).max().orElseThrow();
            double low = dayK.stream().mapToDouble(KLine::getLow).min().orElseThrow();
            if (referenceLine.getClose() < dayK.getFirst().getOpen()) {
                ups[hisUp++ % (hisUpLength - 1)] = high - low;
                fluctuationUpAvg = Arrays.stream(ups).sum() / hisUp;
            } else {
                downs[hisDown++ % (hisDownLength - 1)] = high - low;
                fluctuationDownAvg = Arrays.stream(downs).sum() / hisDown;
            }
        }

        return agg;
    }
}
