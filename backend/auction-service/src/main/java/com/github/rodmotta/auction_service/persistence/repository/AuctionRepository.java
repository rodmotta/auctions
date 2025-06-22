package com.github.rodmotta.auction_service.persistence.repository;

import com.github.rodmotta.auction_service.enums.AuctionStatus;
import com.github.rodmotta.auction_service.persistence.entity.AuctionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface AuctionRepository extends JpaRepository<AuctionEntity, UUID> {
    List<AuctionEntity> findByStatusAndStartDateBefore(AuctionStatus auctionStatus, LocalDateTime now);
    List<AuctionEntity> findByStatusAndEndDateBefore(AuctionStatus auctionStatus, LocalDateTime now);
}
