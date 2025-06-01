package com.github.rodmotta.auction_service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class RestClientConfig {

    @Bean(name = "user-service")
    public RestClient userRestClient(RestClient.Builder builder) {
        return builder
                .baseUrl("http://localhost:8083")
                .build();
    }
}
