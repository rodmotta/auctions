package com.github.rodmotta.auction_service.messaging;

import com.github.rodmotta.auction_service.messaging.event.AuctionFinalizedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import java.util.UUID;

import static com.github.rodmotta.auction_service.config.RabbitConfig.AUCTION_EVENTS_TOPIC;

@Component
public class RabbitProducer {
    private static final Logger logger = LoggerFactory.getLogger(RabbitProducer.class);
    private final RabbitTemplate rabbitTemplate;

    public RabbitProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void publishAuctionFinalizedEvent(UUID auctionId, String auctionTitle) {
        var message = new AuctionFinalizedEvent(auctionId, auctionTitle);
        logger.info("Publishing message for '{}': '{}'.", AUCTION_EVENTS_TOPIC, message);
        rabbitTemplate.convertAndSend(AUCTION_EVENTS_TOPIC, "auction.finalized", message);
    }
}
