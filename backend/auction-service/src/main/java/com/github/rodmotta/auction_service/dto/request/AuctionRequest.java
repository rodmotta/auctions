package com.github.rodmotta.auction_service.dto.request;

import com.github.rodmotta.auction_service.persistence.entity.AuctionEntity;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record AuctionRequest(
        @NotBlank
        @Size(max = 100)
        String title,
        @Size(max = 255)
        String description,
        @NotNull
        @Positive
        BigDecimal startingPrice,
        @NotNull
        @Positive
        BigDecimal minimumIncrement,
        @NotNull
        @Future
        LocalDateTime startDate,
        @NotNull
        @Future
        LocalDateTime endDate
) {
    public AuctionEntity toEntity() {
        return new AuctionEntity(
                title,
                description,
                startingPrice,
                minimumIncrement,
                startDate,
                endDate
        );
    }
}
