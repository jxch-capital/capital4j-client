package org.jxch.capital.client.fx.template;

import javafx.scene.Parent;
import org.jxch.capital.client.service.NamedOrderedService;

public interface ParentTemplate extends NamedOrderedService {

    Parent template(String templateParam, String scriptParam);

    default String getTemplateParam() {
        return null;
    }

    default String getScriptParam() {
        return null;
    }

}
