package org.jxch.capital.client.config;

import okhttp3.OkHttpClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;
import reactor.netty.transport.ProxyProvider;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.time.Duration;
import java.util.Objects;

@Configuration
public class WebConfig {
    @Value("${app.web.codecs-size:#{16 * 1024 * 1024}}")
    private Integer byteCount;
    @Value("${app.web.proxy.host:localhost}")
    private String proxyHost;
    @Value("${app.web.proxy.port:10808}")
    private Integer proxyPort;
    @Value("${app.web.proxy.model:SOCKS5}")
    private String proxyModel;
    @Value("${app.web.timeout-ms:10000}")
    private Long timeoutMs;

    @Bean
    @Primary
    public WebClient.Builder webClientBuilder() {
        return WebClient.builder()
                .exchangeStrategies(ExchangeStrategies.builder()
                        .codecs(configurer -> configurer.defaultCodecs().maxInMemorySize(byteCount))
                        .build())
                .clientConnector(new ReactorClientHttpConnector(
                        HttpClient.create().responseTimeout(Duration.ofMillis(timeoutMs))
                ));
    }

    @Bean
    public WebClient.Builder webClientBuilderProxy(WebClient.Builder webClientBuilder) {
        HttpClient httpClient = HttpClient.create()
                .proxy(proxy -> proxy.type(ProxyProvider.Proxy.valueOf(proxyModel))
                        .host(proxyHost)
                        .port(proxyPort));
        return webClientBuilder.clientConnector(new ReactorClientHttpConnector(httpClient));
    }

    @Bean
    @Primary
    public WebClient webClient(WebClient.Builder webClientBuilder) {
        return webClientBuilder.build();
    }

    @Bean
    public WebClient webClientProxy(WebClient.Builder webClientBuilderProxy) {
        return webClientBuilderProxy.build();
    }

    /*
      之所以还保留 OkHttpClient 而不是全部使用 WebClient
      是因为在爬虫需求中，同样的请求， WebClient 可能会被拒绝访问，而 OkHttpClient 就不会
     */
    @Bean
    @Primary
    public OkHttpClient.Builder okHttpClientBuild() {
        return new OkHttpClient.Builder()
                .connectTimeout(Duration.ofMillis(timeoutMs))
                .readTimeout(Duration.ofMillis(timeoutMs))
                .writeTimeout(Duration.ofMillis(timeoutMs))
                .callTimeout(Duration.ofMillis(timeoutMs));
    }

    @Bean
    public OkHttpClient.Builder okHttpClientBuildProxy(OkHttpClient.Builder okHttpClientBuild) {
        return okHttpClientBuild.proxy(new Proxy(Proxy.Type.valueOf(
                Objects.equals(proxyModel, "HTTP") ? proxyModel : "SOCKS"),
                new InetSocketAddress(proxyHost, proxyPort)));
    }

    @Bean
    @Primary
    public OkHttpClient okHttpClient(OkHttpClient.Builder okHttpClientBuild) {
        return okHttpClientBuild.build();
    }

    @Bean
    public OkHttpClient okHttpClientProxy(OkHttpClient.Builder okHttpClientBuildProxy) {
        return okHttpClientBuildProxy.build();
    }

}
