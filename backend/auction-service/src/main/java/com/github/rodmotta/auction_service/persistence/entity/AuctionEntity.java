package com.github.rodmotta.auction_service.persistence.entity;

import com.github.rodmotta.auction_service.enums.AuctionStatus;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@Entity
@Table(name = "auctions", indexes = {
        @Index(name = "idx_auction_status", columnList = "status"),
        @Index(name = "idx_auction_start_date", columnList = "start_date"),
        @Index(name = "idx_auction_end_date", columnList = "end_date")
})
public class AuctionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String title;
    private String description;
    @Column(precision = 19, scale = 2)
    private BigDecimal startingPrice;
    @Column(precision = 19, scale = 2)
    private BigDecimal currentPrice;
    private BigDecimal minimumIncrement;
    @Column(nullable = false)
    private Integer bidsCounter = 0;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    @Column(nullable = false, length = 20)
    @Enumerated(EnumType.STRING)
    private AuctionStatus status;
    private UUID ownerId;
    private String ownerName;
    private UUID winnerId;
    private String winnerName;
    private LocalDateTime createdAt;

    public AuctionEntity(String title, String description, BigDecimal startingPrice, BigDecimal minimumIncrement, LocalDateTime startDate, LocalDateTime endDate) {
        this.title = title;
        this.description = description;
        this.startingPrice = startingPrice;
        this.currentPrice = startingPrice;
        this.minimumIncrement = minimumIncrement;
        this.bidsCounter = 0;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = AuctionStatus.PENDING;
        this.createdAt = LocalDateTime.now();
    }

    public void incrementBidsCounter() {
        this.bidsCounter++;
    }
}
