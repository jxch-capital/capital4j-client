package org.jxch.capital.client.fx;

import com.sun.javafx.webkit.WebConsoleListener;
import javafx.application.Platform;
import javafx.concurrent.Worker;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.jxch.capital.client.event.JfxStartedEvent;
import org.jxch.capital.client.event.JfxWarmedEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationListener;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class JfxWarm implements ApplicationListener<JfxStartedEvent> {
    private final ResourcePatternResolver resolver;
    private final ApplicationEventPublisher publisher;

    @Override
    @SneakyThrows
    public void onApplicationEvent(@NonNull JfxStartedEvent event) {
        log.info("JAVA FX预热");
        WebConsoleListener.setDefaultListener(
                (webView, message, lineNumber, sourceId) -> log.info("Console: [{}:{}:{}]", sourceId, lineNumber, message)
        );

        Platform.runLater(() -> {
            try {
                WebView warmWebView = new WebView();
                warmWebView.getEngine().loadContent(resolver.getResource("/html/webview_warm.html").getURL().toExternalForm());
                StackPane stackPane = new StackPane();
                stackPane.getChildren().add(warmWebView);
                Scene scene = new Scene(stackPane);
                Stage stage = new Stage();
                stage.setScene(scene);
                stage.initStyle(StageStyle.TRANSPARENT);
                warmWebView.getEngine().getLoadWorker().stateProperty().addListener((observable, oldValue, newValue) -> {
                    if (newValue == Worker.State.SUCCEEDED) {
                        stage.close();
                        log.info("webview 预热完毕");
                        publisher.publishEvent(new JfxWarmedEvent(this));
                    }
                });
                stage.show();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

}
