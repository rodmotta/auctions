package com.github.rodmotta.bid_service.usecase;

import com.github.rodmotta.bid_service.exception.custom.ValidationException;
import com.github.rodmotta.bid_service.messaging.RabbitProducer;
import com.github.rodmotta.bid_service.persistence.entity.BidEntity;
import com.github.rodmotta.bid_service.persistence.repository.BidRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class NotifyAuctionWinnerUseCase {
    private final BidRepository bidRepository;
    private final RabbitProducer rabbitProducer;

    @Transactional
    public void execute(UUID auctionId, String auctionTitle) {
        BidEntity highestBid = bidRepository.findTopByAuctionIdOrderByAmountDesc(auctionId)
                .orElseThrow(() -> new ValidationException("No bids found for this auction"));
        rabbitProducer.publishAuctionWinnerEvent(auctionId, auctionTitle, highestBid.getUserId(), highestBid.getUserName());
    }
}
