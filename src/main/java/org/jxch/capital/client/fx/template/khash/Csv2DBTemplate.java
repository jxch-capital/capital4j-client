package org.jxch.capital.client.fx.template.khash;

import com.alibaba.fastjson2.JSON;
import javafx.scene.Parent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jxch.capital.client.fx.dto.Csv2DBParam;
import org.jxch.capital.client.fx.progress.ProgressBarPane;
import org.jxch.capital.client.fx.progress.ProgressBarService;
import org.jxch.capital.client.fx.template.ParentTemplateService;
import org.jxch.capital.client.khash.rocks.Files2RocksDB;
import org.jxch.capital.client.service.NamedOrderedServices;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Slf4j
@RequiredArgsConstructor
@ParentTemplateService(templateParamType = Csv2DBParam.class)
public class Csv2DBTemplate implements KHashParentTemplate {
    private final ProgressBarService progressBarService;

    @Override
    public Parent template(String templateParam, String scriptParam) {
        Csv2DBParam param = JSON.parseObject(templateParam, Csv2DBParam.class);
        List<File> files = Arrays.stream(Objects.requireNonNull(new File(param.getCsvPath()).listFiles())).toList();

        ProgressBarPane pane = progressBarService.registerProgress(files.size());
        NamedOrderedServices.findServiceByName(Files2RocksDB.class, param.getFiles2DbService()).toRocksDB(files, pane.getUuid());
        return pane.getPane();
    }

    @Override
    public String getName() {
        return "CSV-2DB";
    }

}
