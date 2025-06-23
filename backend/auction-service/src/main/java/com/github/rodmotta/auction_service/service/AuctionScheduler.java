package com.github.rodmotta.auction_service.service;

import com.github.rodmotta.auction_service.messaging.RabbitProducer;
import com.github.rodmotta.auction_service.persistence.entity.AuctionEntity;
import com.github.rodmotta.auction_service.persistence.repository.AuctionRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

import static com.github.rodmotta.auction_service.enums.AuctionStatus.*;

@Service
public class AuctionScheduler {
    private final Logger logger = LoggerFactory.getLogger(AuctionScheduler.class);
    private final AuctionRepository auctionRepository;
    private final RabbitProducer rabbitProducer;

    public AuctionScheduler(AuctionRepository auctionRepository, RabbitProducer rabbitProducer) {
        this.auctionRepository = auctionRepository;
        this.rabbitProducer = rabbitProducer;
    }

    @Scheduled(fixedRate = 60000)
    @Transactional
    public void activateScheduledAuctions() {
        logger.info("Verifying auctions to activate...");
        List<AuctionEntity> auctionsToActivate = auctionRepository.findByStatusAndStartDateBefore(PENDING, LocalDateTime.now());

        if (auctionsToActivate.isEmpty()) return;

        auctionsToActivate.forEach(auction -> auction.setStatus(ACTIVE));
        auctionRepository.saveAll(auctionsToActivate);
    }

    @Scheduled(fixedRate = 60000)
    @Transactional
    public void concludeExpiredAuctions() {
        logger.info("Verifying auctions to conclude...");
        List<AuctionEntity> auctionsToConclude = auctionRepository.findByStatusAndEndDateBefore(ACTIVE, LocalDateTime.now());

        if (auctionsToConclude.isEmpty()) return;

        auctionsToConclude.forEach(auction -> auction.setStatus(COMPLETED));
        auctionRepository.saveAll(auctionsToConclude);

        auctionsToConclude.forEach(rabbitProducer::publishFinalizedAuctionsEvent);
    }
}
