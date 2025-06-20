package com.github.rodmotta.auction_service.dto.response;

import com.github.rodmotta.auction_service.persistence.entity.AuctionEntity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record AuctionResponse(
        UUID id,
        String title,
        String description,
        BigDecimal startingPrice,
        BigDecimal currentPrice,
        BigDecimal minimumIncrement,
        Integer bidsCounter,
        LocalDateTime startDate,
        LocalDateTime endDate,
        UUID ownerId,
        String ownerName
) {

    public AuctionResponse(AuctionEntity auctionEntity) {
        this(
                auctionEntity.getId(),
                auctionEntity.getTitle(),
                auctionEntity.getDescription(),
                auctionEntity.getStartingPrice(),
                auctionEntity.getCurrentPrice(),
                auctionEntity.getMinimumIncrement(),
                auctionEntity.getBidsCounter(),
                auctionEntity.getStartDate(),
                auctionEntity.getEndDate(),
                auctionEntity.getOwnerId(),
                auctionEntity.getOwnerName()
        );
    }
}
