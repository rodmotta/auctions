package com.github.rodmotta.notification_service.messaging.event;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record BidPlacedEvent(
        UUID auctionId,
        String auctionTitle,
        BigDecimal bidAmount,
        UUID bidderId,
        UUID previousBidderId,
        LocalDateTime placedAt
) {
}
