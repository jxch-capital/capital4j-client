package org.jxch.capital.client.fx.dto;

import com.alibaba.fastjson2.JSON;
import com.google.common.io.CharStreams;
import javafx.scene.layout.Pane;
import lombok.*;
import lombok.experimental.Accessors;
import org.springframework.core.io.InputStreamSource;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URL;
import java.nio.file.Path;

@Deprecated
@Data
@Builder
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class TemplateParam<S, C> {
    private Pane tempaltePane;
    private S templateServiceParam;
    private C templateConfigParam;

    public String getTemplateServiceParamString() {
        return param2String(templateServiceParam);
    }

    public String getTemplateConfigParam() {
        return param2String(templateConfigParam);
    }

    @SneakyThrows
    public <T> String param2String(@NonNull T param) {
        if (param.getClass() == String.class) {
            return param.toString();
        }
        if (InputStreamSource.class.isAssignableFrom(param.getClass())) {
            return CharStreams.toString(new InputStreamReader(((InputStreamSource) param).getInputStream()));
        }
        if (param.getClass() == File.class) {
            return CharStreams.toString(new InputStreamReader(new FileInputStream((File)param)));
        }
        if (Path.class.isAssignableFrom(param.getClass())) {
            return CharStreams.toString(new InputStreamReader(new FileInputStream(((Path)param).toFile())));
        }
        if (param.getClass() == URL.class) {
            return CharStreams.toString(new InputStreamReader(((URL)param).openStream()));
        }
        if (param.getClass() == URI.class) {
            return CharStreams.toString(new InputStreamReader(((URI)param).toURL().openStream()));
        }

        return JSON.toJSONString(param);
    }

}
