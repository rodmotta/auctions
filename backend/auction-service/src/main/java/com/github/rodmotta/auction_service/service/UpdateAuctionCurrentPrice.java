package com.github.rodmotta.auction_service.service;

import com.github.rodmotta.auction_service.exception.custom.NotFoundException;
import com.github.rodmotta.auction_service.persistence.entity.AuctionEntity;
import com.github.rodmotta.auction_service.persistence.repository.AuctionRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;

@Service
public class UpdateAuctionCurrentPrice {
    private final AuctionRepository auctionRepository;

    public UpdateAuctionCurrentPrice(AuctionRepository auctionRepository) {
        this.auctionRepository = auctionRepository;
    }

    public void execute(UUID auctionId, BigDecimal amount) {
        AuctionEntity auctionEntity = auctionRepository.findById(auctionId)
                .orElseThrow(() -> new NotFoundException("Auction not found"));

        auctionEntity.incrementBidsCounter();
        auctionEntity.setCurrentPrice(amount);
        auctionRepository.save(auctionEntity);
    }
}
