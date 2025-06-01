package com.github.rodmotta.auction_service.dto.request;

import java.math.BigDecimal;

public record NewBidEvent(
        Long auctionId,
        BigDecimal amount
) {
}
