package com.github.rodmotta.auction_service.messaging.message;

import java.math.BigDecimal;
import java.util.UUID;

public record BidPlacedEventMessage(
        UUID auctionId,
        BigDecimal bidAmount
) {
}
