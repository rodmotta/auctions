package com.github.rodmotta.notification_service.persistence.entity;

import com.github.rodmotta.notification_service.enums.NotificationTypeEnum;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@Entity
@Table(name = "notifications", indexes = {
        @Index(name = "idx_notified_id", columnList = "notified_id")
})
public class NotificationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(nullable = false)
    private UUID auctionId;
    private String auctionTitle;
    private BigDecimal bidAmount;
    @Column(nullable = false)
    private UUID notifiedId;
    private LocalDateTime placedAt;
    @Enumerated(EnumType.STRING)
    private NotificationTypeEnum type;

    public NotificationEntity(UUID auctionId, String auctionTitle, BigDecimal bidAmount, UUID notifiedId, LocalDateTime placedAt, NotificationTypeEnum type) {
        this.auctionId = auctionId;
        this.auctionTitle = auctionTitle;
        this.bidAmount = bidAmount;
        this.notifiedId = notifiedId;
        this.placedAt = placedAt;
        this.type = type;
    }
}
