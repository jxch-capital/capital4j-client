package org.jxch.capital.client.khash.agg;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;
import org.jxch.capital.client.db.pg.dto.KHashCN5M5LDto;
import org.jxch.capital.client.khash.code.GridNumKHashCode;
import org.jxch.capital.client.khash.slicer.DailyKSlicer;
import org.jxch.capital.client.stock.dto.KLine;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

@Data
@Slf4j
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class KLines2KHash2AggKHashCN5M5L implements KLines2KHash2Agg<KHashCN5M5LDto> {
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
    public List<KHashCN5M5LDto> agg(List<KLine> kLines, String code, String ex) {
        List<KHashCN5M5LDto> agg = new ArrayList<>();
        KLine referenceLine = null;
        int hisUp = 0, hisDown = 0;
        double fluctuationDownAvg = 0, fluctuationUpAvg = 0;
        double[] ups = new double[hisUpLength];
        double[] downs = new double[hisDownLength];

        List<List<KLine>> sliceK = dailyKSlicer.slicer(kLines).stream().filter(kl -> !dailyKumValidate || kl.size() == dailyKNum)
                .sorted(Comparator.comparing(list -> list.getFirst().getDate())).toList();

        if (sliceK.size() < hisUpLength + hisDownLength) {
            log.warn("不符合聚合操纵的前置条件，必须给定前{}天的前置数据", hisUpLength + hisDownLength);
            return agg;
        }

        for (List<KLine> dayK : sliceK) {
            dayK = dayK.stream().sorted(Comparator.comparing(KLine::getDate)).toList();
            if (hisUp > hisUpLength && hisDown > hisDownLength) {
                GridNumKHashCode gridNumKHash = GridNumKHashCode.builder()
                        .ranger(ranger)
                        .referenceLine(referenceLine)
                        .fluctuationDown(fluctuationDownAvg)
                        .fluctuationUp(fluctuationUpAvg)
                        .build();
                List<String> fullHashCodeArr = gridNumKHash.fullHashCodeArr(dayK);

                String hash24 = GridNumKHashCode.hash(2, fullHashCodeArr);
                String hash48 = GridNumKHashCode.hash(1, fullHashCodeArr);

                KHashCN5M5LDto dto = KHashCN5M5LDto.builder()
                        .hash5(Integer.parseInt(GridNumKHashCode.hash(10, fullHashCodeArr)))
                        .hash10(Long.parseLong(GridNumKHashCode.hash(5, fullHashCodeArr)))
                        .hash16(Long.parseLong(GridNumKHashCode.hash(3, fullHashCodeArr)))
                        .hash24_1(Long.parseLong(hash24.substring(0, 12)))
                        .hash24_2(Long.parseLong(hash24.substring(12)))
                        .hash48_1(Long.parseLong(hash48.substring(0, 12)))
                        .hash48_2(Long.parseLong(hash48.substring(12, 24)))
                        .hash48_3(Long.parseLong(hash48.substring(24, 36)))
                        .hash48_4(Long.parseLong(hash48.substring(36)))
                        .date(dayK.getFirst().getDate())
                        .codeNumberString(code)
                        .ex(ex)
                        .build();
                agg.add(dto);
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
