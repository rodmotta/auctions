package com.github.rodmotta.auction_service.client;

import com.github.rodmotta.auction_service.dto.response.HighestBidResponse;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.math.BigDecimal;

@Component
public class BidClient {
    private final RestClient restClient;

    public BidClient(@Qualifier("bid-service") RestClient restClient) {
        this.restClient = restClient;
    }

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
