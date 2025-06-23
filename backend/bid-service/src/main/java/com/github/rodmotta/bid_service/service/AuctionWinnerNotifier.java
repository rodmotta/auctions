package com.github.rodmotta.bid_service.service;

import com.github.rodmotta.bid_service.exception.custom.ValidationException;
import com.github.rodmotta.bid_service.messaging.RabbitProducer;
import com.github.rodmotta.bid_service.persistence.entity.BidEntity;
import com.github.rodmotta.bid_service.persistence.repository.BidRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AuctionWinnerNotifier {
    private final BidRepository bidRepository;
    private final RabbitProducer rabbitProducer;

    public AuctionWinnerNotifier(BidRepository bidRepository, RabbitProducer rabbitProducer) {
        this.bidRepository = bidRepository;
        this.rabbitProducer = rabbitProducer;
    }

    @Transactional
    public void execute(UUID auctionId, String auctionTitle) {
        BidEntity highestBid = bidRepository.findTopByAuctionIdOrderByAmountDesc(auctionId)
                .orElseThrow(() -> new ValidationException("No bids found for this auction"));
        rabbitProducer.publishAuctionWinnerEvent(auctionId, auctionTitle, highestBid.getUserId(), highestBid.getUserName());
    }
}
