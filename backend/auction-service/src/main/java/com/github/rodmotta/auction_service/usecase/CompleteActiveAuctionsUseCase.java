package com.github.rodmotta.auction_service.usecase;

import com.github.rodmotta.auction_service.messaging.RabbitProducer;
import com.github.rodmotta.auction_service.persistence.entity.AuctionEntity;
import com.github.rodmotta.auction_service.persistence.repository.AuctionRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

import static com.github.rodmotta.auction_service.enums.AuctionStatus.ACTIVE;
import static com.github.rodmotta.auction_service.enums.AuctionStatus.COMPLETED;

@Component
@Log4j2
@RequiredArgsConstructor
public class CompleteActiveAuctionsUseCase {
    private final AuctionRepository auctionRepository;
    private final RabbitProducer rabbitProducer;

    @Transactional
    public void execute() {
        log.info("Verifying auctions to conclude...");
        List<AuctionEntity> auctionsToConclude = auctionRepository.findByStatusAndEndDateBefore(ACTIVE, LocalDateTime.now());

        if (auctionsToConclude.isEmpty()) return;

        auctionsToConclude.forEach(auction -> auction.setStatus(COMPLETED));
        auctionRepository.saveAll(auctionsToConclude);

        auctionsToConclude.forEach(rabbitProducer::publishFinalizedAuctionsEvent);
    }
}
