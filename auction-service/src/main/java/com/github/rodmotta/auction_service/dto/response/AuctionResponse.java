package com.github.rodmotta.auction_service.dto.response;

import com.github.rodmotta.auction_service.entity.AuctionEntity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record AuctionResponse(
        Long id,
        String title,
        BigDecimal currentBid,
        LocalDateTime endTime
) {
    public AuctionResponse(AuctionEntity auctionEntity) {
        this(
                auctionEntity.getId(),
                auctionEntity.getTitle(),
                auctionEntity.getStartingBid(),
                auctionEntity.getEndTime()
        );
    }

    public AuctionResponse(AuctionEntity auctionEntity, BigDecimal currentBid) {
        this(
                auctionEntity.getId(),
                auctionEntity.getTitle(),
                currentBid,
                auctionEntity.getEndTime()
        );
    }
}
