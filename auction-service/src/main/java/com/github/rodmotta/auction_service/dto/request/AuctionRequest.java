package com.github.rodmotta.auction_service.dto.request;

import com.github.rodmotta.auction_service.entity.AuctionEntity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record AuctionRequest(
        String title,
        BigDecimal initialPrice,
        BigDecimal minimumIncrement,
        LocalDateTime startTime,
        LocalDateTime endTime
) {
    public AuctionEntity toEntity() {
        return new AuctionEntity(title, initialPrice, minimumIncrement, startTime, endTime);
    }
}
