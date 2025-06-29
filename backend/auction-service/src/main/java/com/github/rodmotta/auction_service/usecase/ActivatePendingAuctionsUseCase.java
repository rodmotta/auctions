package com.github.rodmotta.auction_service.usecase;

import com.github.rodmotta.auction_service.persistence.entity.AuctionEntity;
import com.github.rodmotta.auction_service.persistence.repository.AuctionRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

import static com.github.rodmotta.auction_service.enums.AuctionStatus.ACTIVE;
import static com.github.rodmotta.auction_service.enums.AuctionStatus.PENDING;

@Component
@Log4j2
@RequiredArgsConstructor
public class ActivatePendingAuctionsUseCase {
    private final AuctionRepository auctionRepository;

    @Transactional
    public void execute() {
        log.info("Verifying auctions to activate...");
        List<AuctionEntity> auctionsToActivate = auctionRepository.findByStatusAndStartDateBefore(PENDING, LocalDateTime.now());

        if (auctionsToActivate.isEmpty()) return;

        auctionsToActivate.forEach(auction -> auction.setStatus(ACTIVE));
        auctionRepository.saveAll(auctionsToActivate);
    }
}
