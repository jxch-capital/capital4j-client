package org.jxch.capital.client.khash.tree;

import cn.hutool.extra.spring.SpringUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.jxch.capital.client.config.ThreadConfig;
import org.jxch.capital.client.event.ProgressBarEvent;
import org.jxch.capital.client.khash.agg.DailyKLineFullHashCodeAgg;
import org.jxch.capital.client.khash.agg.KLineFullHashCodeAgg;
import org.jxch.capital.client.khash.code.GridNumKHashCode;
import org.jxch.capital.client.khash.neo4j.KHashNodeServices;
import org.jxch.capital.client.db.neo4j.node.KHashNodeData;
import org.jxch.capital.client.khash.reader.CsvBaostockKLineFileReader;
import org.jxch.capital.client.stock.dto.KLine;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class CsvBaostock5M2HashTree2Neo4J implements Files2HashTree {
    private final CsvBaostockKLineFileReader csvBaostockKLineFileReader;

    @Override
    public void toHashTree2Neo4J(@NotNull List<File> files) {
        DailyKLineFullHashCodeAgg agg = new DailyKLineFullHashCodeAgg();
        files.forEach(file -> SpringUtil.getBean(CsvBaostock5M2HashTree2Neo4J.class).toHashTree2Neo4J(file, agg));
    }

    @Async(ThreadConfig.IO_THREAD_POOL)
    public void toHashTree2Neo4J(@NotNull File file, @NotNull KLineFullHashCodeAgg agg) {
        String[] nameSplit = file.getName().split("_");
        String code = nameSplit[0];
        List<KLine> kLines = csvBaostockKLineFileReader.readeAll(file);
        Map<List<String>, KHashNodeData> kHashNodeDataMap = agg.agg(kLines, code);

        kHashNodeDataMap.forEach((fullCode, value) -> {
            KHashNodeServices.save(GridNumKHashCode.hashFull(10, fullCode), "5_cn", value);
            KHashNodeServices.save(GridNumKHashCode.hashFull(5, fullCode), "10_cn", value);
            KHashNodeServices.save(GridNumKHashCode.hashFull(3, fullCode), "16_cn", value);
            KHashNodeServices.save(GridNumKHashCode.hashFull(2, fullCode), "24_cn", value);
            KHashNodeServices.save(fullCode, "48_cn", value);
        });
    }

    @Override
    public void toHashTree2Neo4J(@NotNull List<File> files, String uuid) {
        DailyKLineFullHashCodeAgg agg = new DailyKLineFullHashCodeAgg();
        files.forEach(file -> SpringUtil.getBean(CsvBaostock5M2HashTree2Neo4J.class).toHashTree2Neo4J(file, agg, uuid));
    }

    @Async(ThreadConfig.IO_THREAD_POOL)
    public void toHashTree2Neo4J(@NotNull File file, @NotNull KLineFullHashCodeAgg agg, String uuid) {
        try {
            toHashTree2Neo4J(file, agg);
            SpringUtil.publishEvent(new ProgressBarEvent(this).setNum(1).setSucceed(true).setUuid(uuid));
        } catch (Throwable t) {
            SpringUtil.publishEvent(new ProgressBarEvent(this).setNum(1).setSucceed(false).setUuid(uuid).setErrorMsg(t.getMessage()));
        }
    }


    @Override
    public String getName() {
        return "CsvBaostock5M2HashTree2Neo4J";
    }

}
