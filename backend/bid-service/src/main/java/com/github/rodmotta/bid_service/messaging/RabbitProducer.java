package com.github.rodmotta.bid_service.messaging;

import com.github.rodmotta.bid_service.dto.response.AuctionResponse;
import com.github.rodmotta.bid_service.messaging.event.AuctionWinnerEvent;
import com.github.rodmotta.bid_service.messaging.event.BidPlacedEvent;
import com.github.rodmotta.bid_service.persistence.entity.BidEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

import static com.github.rodmotta.bid_service.config.RabbitConfig.AUCTION_EVENTS_TOPIC;

@Component
@RequiredArgsConstructor
public class RabbitProducer {
    private final RabbitTemplate rabbitTemplate;

    public void publishBidPlacedEvent(BidEntity bidEntity, AuctionResponse auctionResponse, UUID bidderId, BidEntity currentHighestBid, LocalDateTime placedAt) {

        UUID previousBidderId = Objects.nonNull(currentHighestBid)
                ? currentHighestBid.getUserId()
                : null;

        BidPlacedEvent message = new BidPlacedEvent(
                auctionResponse.id(),
                auctionResponse.title(),
                bidEntity.getAmount(),
                bidderId,
                previousBidderId,
                placedAt);
        rabbitTemplate.convertAndSend(AUCTION_EVENTS_TOPIC, "bid.placed", message);
    }

    public void publishAuctionWinnerEvent(UUID auctionId, String auctionTitle, UUID winnerId, String winnerName) {
        AuctionWinnerEvent message = new AuctionWinnerEvent(auctionId, auctionTitle, winnerId, winnerName);
        rabbitTemplate.convertAndSend(AUCTION_EVENTS_TOPIC, "auction.winner", message);
    }
}
