package com.github.rodmotta.auction_service.service;

import com.github.rodmotta.auction_service.exception.custom.NotFoundException;
import com.github.rodmotta.auction_service.persistence.entity.AuctionEntity;
import com.github.rodmotta.auction_service.persistence.repository.AuctionRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UpdateAuctionWinner {
    private final AuctionRepository auctionRepository;

    public UpdateAuctionWinner(AuctionRepository auctionRepository) {
        this.auctionRepository = auctionRepository;
    }

    public void execute(UUID auctionId, UUID winnerId, String winnerName) {
        AuctionEntity auctionEntity = auctionRepository.findById(auctionId)
                .orElseThrow(() -> new NotFoundException("Auction not found"));

        auctionEntity.setWinnerId(winnerId);
        auctionEntity.setWinnerName(winnerName);
        auctionRepository.save(auctionEntity);
    }
}
