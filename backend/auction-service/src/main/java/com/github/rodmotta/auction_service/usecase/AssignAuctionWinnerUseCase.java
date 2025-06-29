package com.github.rodmotta.auction_service.usecase;

import com.github.rodmotta.auction_service.exception.custom.NotFoundException;
import com.github.rodmotta.auction_service.persistence.entity.AuctionEntity;
import com.github.rodmotta.auction_service.persistence.repository.AuctionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class AssignAuctionWinnerUseCase {
    private final AuctionRepository auctionRepository;

    public void execute(UUID auctionId, UUID winnerId, String winnerName) {
        AuctionEntity auctionEntity = auctionRepository.findById(auctionId)
                .orElseThrow(() -> new NotFoundException("Auction not found"));

        auctionEntity.setWinnerId(winnerId);
        auctionEntity.setWinnerName(winnerName);
        auctionRepository.save(auctionEntity);
    }
}
