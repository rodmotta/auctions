package com.github.rodmotta.bid_service.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record AuctionResponse(
        UUID id,
        String title,
        BigDecimal currentPrice,
        BigDecimal startingPrice,
        BigDecimal minimumIncrement,
        LocalDateTime endDate,
        UUID ownerId
) {
}
