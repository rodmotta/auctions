package com.github.rodmotta.auction_service.client;

import com.github.rodmotta.auction_service.dto.response.HighestBidResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.math.BigDecimal;

@Component
public class BidClient {
    private final RestClient restClient = RestClient.create("http://localhost:8082");

    public BigDecimal getHighestBidByAuctionId(Long auctionId) {
        var response = restClient.get()
                .uri("/bids/auction/{auctionId}/highest", auctionId)
                .retrieve()
                .onStatus(t -> {
                    System.out.println(t.getStatusCode());
                    return true;
                })
                .body(HighestBidResponse.class);
        return response.highestBid(); //fixme: may produce nullpointer
    }
}
