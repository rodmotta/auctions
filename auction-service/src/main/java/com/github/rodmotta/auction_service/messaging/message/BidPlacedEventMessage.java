package com.github.rodmotta.auction_service.messaging.message;

import java.math.BigDecimal;

public record BidPlacedEventMessage(
        Long auctionId,
        BigDecimal bidAmount
) {
}
