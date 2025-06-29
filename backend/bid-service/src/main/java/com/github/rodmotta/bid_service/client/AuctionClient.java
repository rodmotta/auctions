package com.github.rodmotta.bid_service.client;

import com.github.rodmotta.bid_service.dto.response.AuctionResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.Optional;
import java.util.UUID;

@Component
public class AuctionClient {
    private final RestClient restClient = RestClient.create("http://localhost:8081");

    public Optional<AuctionResponse> getAuctionById(UUID auctionId) {
        return Optional.ofNullable(
                restClient.get()
                        .uri("/auctions/{auctionId}", auctionId)
                        .retrieve()
                        .body(AuctionResponse.class));
    }
}
