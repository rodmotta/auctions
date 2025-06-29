package com.github.rodmotta.auction_service.usecase;

import com.github.rodmotta.auction_service.exception.custom.NotFoundException;
import com.github.rodmotta.auction_service.persistence.entity.AuctionEntity;
import com.github.rodmotta.auction_service.persistence.repository.AuctionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class UpdateAuctionBidUseCase {
    private final AuctionRepository auctionRepository;

    public void execute(UUID auctionId, BigDecimal amount) {
        AuctionEntity auctionEntity = auctionRepository.findById(auctionId)
                .orElseThrow(() -> new NotFoundException("Auction not found"));

        auctionEntity.incrementBidsCounter();
        auctionEntity.setCurrentPrice(amount);
        auctionRepository.save(auctionEntity);
    }
}
