package com.github.rodmotta.auction_service.dto.response;

import com.github.rodmotta.auction_service.persistence.entity.AuctionEntity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record AuctionResponse(
        Long id,
        String title,
        String description,
        BigDecimal startingPrice,
        BigDecimal currentPrice,
        BigDecimal minimumIncrement,
        LocalDateTime endDate,
        String ownerId,
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
                auctionEntity.getEndDate(),
                auctionEntity.getOwnerId(),
                auctionEntity.getOwnerName()
        );
    }
}
