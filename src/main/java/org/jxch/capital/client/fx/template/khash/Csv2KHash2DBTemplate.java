package org.jxch.capital.client.fx.template.khash;

import com.alibaba.fastjson2.JSON;
import javafx.scene.Parent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jxch.capital.client.fx.dto.Csv2KHash2DBParam;
import org.jxch.capital.client.fx.progress.ProgressBarPane;
import org.jxch.capital.client.fx.progress.ProgressBarService;
import org.jxch.capital.client.fx.template.ParentTemplateService;
import org.jxch.capital.client.khash.hash2db.Files2Hash2DB;
import org.jxch.capital.client.service.NamedOrderedServices;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Slf4j
@RequiredArgsConstructor
@ParentTemplateService(templateParamType = Csv2KHash2DBParam.class)
public class Csv2KHash2DBTemplate implements KHashParentTemplate {
    private final ProgressBarService progressBarService;

    @Override
    public Parent template(String templateParam, String scriptParam) {
        Csv2KHash2DBParam param = JSON.parseObject(templateParam, Csv2KHash2DBParam.class);
        List<File> files;
        if (Objects.isNull(param.getFileNames()) || param.getFileNames().isEmpty()) {
            files = Arrays.stream(Objects.requireNonNull(new File(param.getCsvPath()).listFiles())).toList();
        } else {
            files = param.getFileNames().stream().map(fileName -> new File(param.getCsvPath()).toPath().resolve(fileName).toFile()).toList();
        }

        ProgressBarPane pane = progressBarService.registerProgress(files.size());
        NamedOrderedServices.findServiceByName(Files2Hash2DB.class, param.getFiles2Hash2DbService()).toHash2DB(files, pane.getUuid());
        return pane.getPane();
    }

    @Override
    public String getName() {
        return "Csv2KHash2DB";
    }

}
