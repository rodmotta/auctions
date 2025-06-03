package com.github.rodmotta.bid_service.dto.request;

import com.github.rodmotta.bid_service.entity.BidEntity;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record BidRequest(
        @NotNull
        Long auctionId,
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
