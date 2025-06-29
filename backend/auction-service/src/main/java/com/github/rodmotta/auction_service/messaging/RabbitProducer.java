package com.github.rodmotta.auction_service.messaging;

import com.github.rodmotta.auction_service.messaging.event.AuctionFinalizedEvent;
import com.github.rodmotta.auction_service.persistence.entity.AuctionEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import static com.github.rodmotta.auction_service.config.RabbitConfig.AUCTION_EVENTS_TOPIC;

@Component
@RequiredArgsConstructor
public class RabbitProducer {
    private final RabbitTemplate rabbitTemplate;

    public void publishFinalizedAuctionsEvent(AuctionEntity auction) {
        var message = new AuctionFinalizedEvent(auction);
        rabbitTemplate.convertAndSend(AUCTION_EVENTS_TOPIC, "auction.finalized", message);
    }
}
