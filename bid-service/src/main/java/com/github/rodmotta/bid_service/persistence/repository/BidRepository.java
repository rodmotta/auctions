package com.github.rodmotta.bid_service.persistence.repository;

import com.github.rodmotta.bid_service.persistence.entity.BidEntity;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BidRepository extends JpaRepository<BidEntity, UUID> {
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<BidEntity> findTopByAuctionIdOrderByAmountDesc(UUID auctionId);
    List<BidEntity> findTop5ByAuctionIdOrderByAmountDesc(UUID auctionId);
}
