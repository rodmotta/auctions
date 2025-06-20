package com.github.rodmotta.auth_service.client;

import com.github.rodmotta.auth_service.dto.response.KeycloakTokenResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClient;

import static org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED;

@Component
public class KeycloakClient {

    private final RestClient restClient = RestClient.create("http://localhost:8000");

    public KeycloakTokenResponse exchangeCode(String code, String redirectUri) {
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "authorization_code");
        body.add("code", code);
        body.add("client_id", "auction-app");
        body.add("redirect_uri", redirectUri);

        return restClient.post()
                .uri("/realms/auction-realm/protocol/openid-connect/token")
                .body(body)
                .contentType(APPLICATION_FORM_URLENCODED)
                .retrieve()
                .body(KeycloakTokenResponse.class);
    }

    public KeycloakTokenResponse refreshToken(String refreshToken) {
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "refresh_token");
        body.add("refresh_token", refreshToken);
        body.add("client_id", "auction-app");

        return restClient.post()
                .uri("/realms/auction-realm/protocol/openid-connect/token")
                .body(body)
                .contentType(APPLICATION_FORM_URLENCODED)
                .retrieve()
                .body(KeycloakTokenResponse.class);
    }

    public void logout(String refreshToken) {
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("refresh_token", refreshToken);
        body.add("client_id", "auction-app");

        restClient.post()
                .uri("/realms/auction-realm/protocol/openid-connect/logout")
                .body(body)
                .contentType(APPLICATION_FORM_URLENCODED)
                .retrieve();
    }
}
