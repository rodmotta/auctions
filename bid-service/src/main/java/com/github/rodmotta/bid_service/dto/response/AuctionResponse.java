package com.github.rodmotta.bid_service.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record AuctionResponse(
        Long id,
        String title,
        BigDecimal currentBid,
        LocalDateTime endTime
) {
}
