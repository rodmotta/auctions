package com.github.rodmotta.notification_service.persistence.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "notifications")
public class NotificationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private UUID auctionId;
    private String auctionTitle;
    private BigDecimal bidAmount;
    private UUID notifiedId;
    private LocalDateTime placedAt;

    public NotificationEntity() {
    }

    public NotificationEntity(UUID auctionId, String auctionTitle, BigDecimal bidAmount, UUID notifiedId, LocalDateTime placedAt) {
        this.auctionId = auctionId;
        this.auctionTitle = auctionTitle;
        this.bidAmount = bidAmount;
        this.notifiedId = notifiedId;
        this.placedAt = placedAt;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getAuctionId() {
        return auctionId;
    }

    public void setAuctionId(UUID auctionId) {
        this.auctionId = auctionId;
    }

    public String getAuctionTitle() {
        return auctionTitle;
    }

    public void setAuctionTitle(String auctionTitle) {
        this.auctionTitle = auctionTitle;
    }

    public BigDecimal getBidAmount() {
        return bidAmount;
    }

    public void setBidAmount(BigDecimal bidAmount) {
        this.bidAmount = bidAmount;
    }

    public UUID getNotifiedId() {
        return notifiedId;
    }

    public void setNotifiedId(UUID notifiedId) {
        this.notifiedId = notifiedId;
    }

    public LocalDateTime getPlacedAt() {
        return placedAt;
    }

    public void setPlacedAt(LocalDateTime placedAt) {
        this.placedAt = placedAt;
    }
}
