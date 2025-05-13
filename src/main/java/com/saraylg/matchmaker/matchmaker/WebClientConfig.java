
package com.saraylg.matchmaker.matchmaker;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Bean
    public WebClient webClient(WebClient.Builder builder) {
        return builder.build();
    }

    @Bean
    public WebClient steamWebClient() {
        return WebClient.builder()
                .baseUrl("https://api.steampowered.com")
                .build();
    }
}

