package com.github.rodmotta.auction_service.repository;

import com.github.rodmotta.auction_service.entity.AuctionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuctionRepository extends JpaRepository<AuctionEntity, Long> {
}
