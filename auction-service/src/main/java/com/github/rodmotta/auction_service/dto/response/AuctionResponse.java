package com.github.rodmotta.auction_service.dto.response;

import com.github.rodmotta.auction_service.entity.AuctionEntity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record AuctionResponse(
        Long id,
        String title,
        BigDecimal startingBid,
        BigDecimal currentBid,
        LocalDateTime endTime,
        String sellerName
) {

    public AuctionResponse(AuctionEntity auctionEntity) {
        this(
                auctionEntity.getId(),
                auctionEntity.getTitle(),
                auctionEntity.getStartingBid(),
                auctionEntity.getCurrentBid(),
                auctionEntity.getEndTime(),
                auctionEntity.getUserName()
        );
    }
}
