package com.github.rodmotta.auction_service.messaging.model;

import java.math.BigDecimal;

public record UpdateCurrentPriceEventMessage(
        Long auctionId,
        BigDecimal amount
) {
}
