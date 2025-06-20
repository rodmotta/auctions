package com.github.rodmotta.auction_service.persistence.repository;

import com.github.rodmotta.auction_service.persistence.entity.AuctionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AuctionRepository extends JpaRepository<AuctionEntity, UUID> {
}
