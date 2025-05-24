package com.github.rodmotta.bid_service.repository;

import com.github.rodmotta.bid_service.entity.BidEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BidRepository extends JpaRepository<BidEntity, Long> {
    Optional<BidEntity> findTopByAuctionIdOrderByAmountDesc(Long auctionId);
    List<BidEntity> findByAuctionIdOrderByAmountDesc(Long auctionId);
}
