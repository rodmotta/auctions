package com.github.rodmotta.auction_service.messaging.event;

import com.github.rodmotta.auction_service.persistence.entity.AuctionEntity;

import java.util.UUID;

public record AuctionFinalizedEvent(
        UUID auctionId,
        String auctionTitle
) {
    public AuctionFinalizedEvent(AuctionEntity auctionEntity) {
        this(
                auctionEntity.getId(),
                auctionEntity.getTitle()
        );
    }
}
