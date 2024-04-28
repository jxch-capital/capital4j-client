package org.jxch.capital.client.fx.template.khash;

import javafx.scene.Parent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jxch.capital.client.fx.dto.Csv2HashTreeParam;
import org.jxch.capital.client.fx.progress.ProgressBarService;
import org.jxch.capital.client.fx.template.ParentTemplateService;

@Slf4j
@RequiredArgsConstructor
@ParentTemplateService(templateParamType = Csv2HashTreeParam.class)
public class Csv2HashTree2Neo4JTemplate implements KHashParentTemplate{
    private final ProgressBarService progressBarService;

    @Override
    public Parent template(String templateParam, String scriptParam) {

        return null;
    }

    @Override
    public String getName() {
        return "CSV-2HashTree2Neo4J";
    }
}
