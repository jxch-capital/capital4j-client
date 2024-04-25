package org.jxch.capital.client.fx;

import com.sun.javafx.webkit.WebConsoleListener;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.jxch.capital.client.event.life.JfxStartedEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationListener;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class AppWarm implements ApplicationListener<JfxStartedEvent> {
    private final ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
    private final ApplicationEventPublisher publisher;

    @Override
    @SneakyThrows
    public void onApplicationEvent(@NonNull JfxStartedEvent event) {
        log.info("JAVA FX预热");
        WebConsoleListener.setDefaultListener(
                (webView, message, lineNumber, sourceId) -> log.info("Console: [{}:{}:{}]", sourceId, lineNumber, message)
        );

    }

}
