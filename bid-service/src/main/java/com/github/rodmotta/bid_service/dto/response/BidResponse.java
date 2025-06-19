package com.github.rodmotta.bid_service.dto.response;

import com.github.rodmotta.bid_service.persistence.entity.BidEntity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record BidResponse(
        UUID id,
        UUID auctionId,
        String bidderName,
        BigDecimal amount,
        LocalDateTime timestamp
) {
    public BidResponse(BidEntity bidEntity) {
        this(
                bidEntity.getId(),
                bidEntity.getAuctionId(),
                bidEntity.getUserName(),
                bidEntity.getAmount(),
                bidEntity.getCreatedAt()
        );
    }
}
