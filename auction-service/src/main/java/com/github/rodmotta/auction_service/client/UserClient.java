package com.github.rodmotta.auction_service.client;

import com.github.rodmotta.auction_service.dto.response.UserResponse;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class UserClient {
    private final RestClient restClient;

    public UserClient(@Qualifier("user-service") RestClient restClient) {
        this.restClient = restClient;
    }

    public UserResponse getUserName(Long userId) {
        return restClient.get()
                .uri("/users/{userId}", userId)
                .retrieve()
                .body(UserResponse.class); //fixme: may produce nullpointer
    }
}
