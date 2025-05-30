package com.github.rodmotta.auction_service.dto.response;

import com.github.rodmotta.auction_service.entity.AuctionEntity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record AuctionResponse(
        Long id,
        String title,
        BigDecimal startingBid,
        LocalDateTime endTime,
        BigDecimal currentBid,
        String sellerName
) {

    public AuctionResponse(AuctionEntity auctionEntity, BigDecimal currentBid, String sellerName) {
        this(
                auctionEntity.getId(),
                auctionEntity.getTitle(),
                auctionEntity.getStartingBid(),
                auctionEntity.getEndTime(),
                currentBid,
                sellerName
        );
    }
}
