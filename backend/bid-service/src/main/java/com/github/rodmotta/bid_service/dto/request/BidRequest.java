package com.github.rodmotta.bid_service.dto.request;

import com.github.rodmotta.bid_service.persistence.entity.BidEntity;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.util.UUID;

public record BidRequest(
        @NotNull
        UUID auctionId,
        @NotNull
        @Positive
        BigDecimal amount
) {
    public BidEntity toEntity() {
        return new BidEntity(
                auctionId,
                amount
        );
    }
}
