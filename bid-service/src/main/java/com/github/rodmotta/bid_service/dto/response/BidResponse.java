package com.github.rodmotta.bid_service.dto.response;

import com.github.rodmotta.bid_service.entity.BidEntity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record BidResponse(
        Long id,
        Long auctionId,
        Long userId,
        BigDecimal amount,
        LocalDateTime timestamp
) {
    public BidResponse(BidEntity bidEntity) {
        this(
                bidEntity.getId(),
                bidEntity.getAuctionId(),
                bidEntity.getUserId(),
                bidEntity.getAmount(),
                bidEntity.getTimestamp()
        );
    }
}
