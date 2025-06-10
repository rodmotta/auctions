package com.github.rodmotta.notification_service.messaging.model;

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
