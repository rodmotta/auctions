package com.github.rodmotta.bid_service.messaging.message;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record BidPlacedEventMessage(
        Long auctionId,
        String auctionTitle,
        BigDecimal bidAmount,
        String bidderId,
        String previousBidderId,
        LocalDateTime placedAt
) {
}
