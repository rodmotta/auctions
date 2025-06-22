package com.github.rodmotta.auction_service.messaging.event;

import java.math.BigDecimal;
import java.util.UUID;

public record BidPlacedEvent(
        UUID auctionId,
        BigDecimal bidAmount
) {
}
