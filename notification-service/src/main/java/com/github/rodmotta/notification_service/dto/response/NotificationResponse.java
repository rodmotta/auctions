package com.github.rodmotta.notification_service.dto.response;

import com.github.rodmotta.notification_service.persistence.entity.NotificationEntity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record NotificationResponse(
        Long id,
        Long auctionId,
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
