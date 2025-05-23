package com.github.rodmotta.bid_service.dto.request;

import java.math.BigDecimal;

public record BidRequest(
        Long auctionId,
        Long userId,
        BigDecimal amount
) {
}
