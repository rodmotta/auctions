package com.github.rodmotta.bid_service.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record BidResponse(
        Long id,
        Long auctionId,
        Long userId,
        BigDecimal amount,
        LocalDateTime timestamp
) {
}
