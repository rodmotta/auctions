package com.github.rodmotta.bid_service.dto.request;

import com.github.rodmotta.bid_service.entity.BidEntity;

import java.math.BigDecimal;

public record BidRequest(
        Long auctionId,
        BigDecimal amount
) {
    public BidEntity toEntity() {
        return new BidEntity(
                auctionId,
                amount
        );
    }
}
