package org.jxch.capital.client.khash.slicer;

import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.jxch.capital.client.stock.dto.KLine;

import java.time.ZoneId;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class DailyKSlicer implements KSlicer {

    @Override
    public List<List<KLine>> slicer(@NotNull List<KLine> kLines) {
        return kLines.stream().collect(Collectors.groupingBy(
                        kLine -> kLine.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate()))
                .values().stream().toList();
    }

}