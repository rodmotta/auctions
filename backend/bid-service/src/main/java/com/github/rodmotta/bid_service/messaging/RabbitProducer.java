package com.github.rodmotta.bid_service.messaging;

import com.github.rodmotta.bid_service.dto.response.AuctionResponse;
import com.github.rodmotta.bid_service.messaging.event.AuctionWinnerEvent;
import com.github.rodmotta.bid_service.messaging.event.BidPlacedEvent;
import com.github.rodmotta.bid_service.persistence.entity.BidEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;

import static com.github.rodmotta.bid_service.config.RabbitConfig.AUCTION_EVENTS_TOPIC;

@Component
public class RabbitProducer {
    private final Logger logger = LoggerFactory.getLogger(RabbitProducer.class);
    private final RabbitTemplate rabbitTemplate;

    public RabbitProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void publishBidPlacedEvent(BidEntity bidEntity, AuctionResponse auctionResponse, UUID bidderId, UUID previousBidderId, LocalDateTime placedAt) {
        logger.debug("Publishing bid placed event for auction ID {}. New bid ID: {}", bidEntity.getAuctionId(), bidEntity.getId());
        BidPlacedEvent message = new BidPlacedEvent(
                auctionResponse.id(),
                auctionResponse.title(),
                bidEntity.getAmount(),
                bidderId,
                previousBidderId,
                placedAt);

        rabbitTemplate.convertAndSend(AUCTION_EVENTS_TOPIC, "bid.placed", message);
        logger.debug("Bid placed event published for auction ID {}.", bidEntity.getAuctionId());
    }

    public void publishAuctionWinnerEvent(UUID auctionId, String auctionTitle, UUID winnerId, String winnerName) {
        logger.debug("Publishing auction winner event for auction ID {}. Winner ID: {}", auctionId, winnerId);
        var message = new AuctionWinnerEvent(auctionId, auctionTitle, winnerId, winnerName);

        rabbitTemplate.convertAndSend(AUCTION_EVENTS_TOPIC, "auction.winner", message);
        logger.debug("Auction winner event published for auction ID {}.", auctionId);
    }
}
