package com.github.rodmotta.notification_service.dto.response;

import com.github.rodmotta.notification_service.persistence.entity.NotificationEntity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record NotificationResponse(
        UUID id,
        UUID auctionId,
        String auctionTitle,
        BigDecimal bidAmount,
        LocalDateTime placedAt
) {
    public NotificationResponse(NotificationEntity notificationEntity) {
        this(
                notificationEntity.getId(),
                notificationEntity.getAuctionId(),
                notificationEntity.getAuctionTitle(),
                notificationEntity.getBidAmount(),
                notificationEntity.getPlacedAt()
        );
    }
}
