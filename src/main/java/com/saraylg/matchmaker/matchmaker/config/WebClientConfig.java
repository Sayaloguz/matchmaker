package com.saraylg.matchmaker.matchmaker.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

@Configuration
public class WebClientConfig {

    // WebClient genérico
    @Bean
    public WebClient webClient(WebClient.Builder builder) {
        return builder.build();
    }

    // WebClient Steam para GetAppList)
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

    // WebClient Steam para appdetails
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
