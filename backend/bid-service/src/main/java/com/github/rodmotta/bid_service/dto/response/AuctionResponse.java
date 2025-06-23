package com.github.rodmotta.bid_service.dto.response;

import com.github.rodmotta.bid_service.enums.AuctionStatus;
import com.github.rodmotta.bid_service.exception.custom.ValidationException;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import static com.github.rodmotta.bid_service.enums.AuctionStatus.COMPLETED;
import static com.github.rodmotta.bid_service.enums.AuctionStatus.PENDING;

public record AuctionResponse(
        UUID id,
        String title,
        BigDecimal currentPrice,
        BigDecimal startingPrice,
        BigDecimal minimumIncrement,
        LocalDateTime endDate,
        LocalDateTime startDate,
        AuctionStatus status,
        UUID ownerId
) {

    public void assertValidToBid(LocalDateTime dateTime) {
        if (dateTime.isBefore(startDate) || status.equals(PENDING)) {
            throw new ValidationException("The auction has not started");
        }

        if (dateTime.isAfter(endDate) || status.equals(COMPLETED)) {
            throw new ValidationException("The auction has ended");
        }
    }
}
