package com.saraylg.matchmaker.matchmaker;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

@Configuration
public class WebClientConfig {

    /** WebClient genérico (ya lo usas en otras partes) */
    @Bean
    public WebClient webClient(WebClient.Builder builder) {
        return builder.build();
    }

    /** WebClient para la Web API oficial (p. ej. GetAppList) */
    @Bean(name = "steamWebClient")
    public WebClient steamWebClient() {
        HttpClient httpClient = HttpClient.create().compress(true);

        return WebClient.builder()
                .baseUrl("https://api.steampowered.com")
                .clientConnector(new ReactorClientHttpConnector(httpClient))
                .codecs(c -> c.defaultCodecs().maxInMemorySize(32 * 1024 * 1024))
                .defaultHeader("User-Agent", "matchmaker-sync/1.0")
                .build();
    }

    /** WebClient para la Store API (appdetails) */
    @Bean(name = "storeWebClient")
    public WebClient storeWebClient() {
        HttpClient httpClient = HttpClient.create().compress(true);

        return WebClient.builder()
                .baseUrl("https://store.steampowered.com/api")
                .clientConnector(new ReactorClientHttpConnector(httpClient))
                .codecs(c -> c.defaultCodecs().maxInMemorySize(32 * 1024 * 1024))
                .defaultHeader("User-Agent", "matchmaker-sync/1.0")
                .build();
    }
}
