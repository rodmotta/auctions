package com.github.rodmotta.bid_service.messaging.model;

import java.math.BigDecimal;

public record UpdateCurrentPriceEventMessage(
        Long auctionId,
        BigDecimal amount
) {
}
