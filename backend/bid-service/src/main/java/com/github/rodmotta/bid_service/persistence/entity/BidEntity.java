package com.github.rodmotta.bid_service.persistence.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@Entity
@Table(name = "bids", indexes = {
        @Index(name = "idx_auction_id", columnList = "auction_id")
})
public class BidEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(nullable = false)
    private UUID auctionId;
    @Column(nullable = false)
    private UUID userId;
    @Column(nullable = false)
    private String userName;
    @Column(precision = 19, scale = 2, nullable = false)
    private BigDecimal amount;
    @Column(nullable = false)
    private LocalDateTime createdAt;

    public BidEntity(UUID auctionId, BigDecimal amount) {
        this.auctionId = auctionId;
        this.amount = amount;
    }
}
