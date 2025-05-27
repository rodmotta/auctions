package com.github.rodmotta.auction_service.dto.request;

import com.github.rodmotta.auction_service.entity.AuctionEntity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record AuctionRequest(
        String title,
        String description,
        BigDecimal startingBid,
        LocalDateTime endTime
) {
    public AuctionEntity toEntity(Long userId) {
        return new AuctionEntity(
                title,
                description,
                startingBid,
                endTime,
                userId
        );
    }
}
