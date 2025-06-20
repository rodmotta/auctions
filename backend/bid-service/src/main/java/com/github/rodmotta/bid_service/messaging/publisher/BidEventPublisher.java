package com.github.rodmotta.bid_service.messaging.publisher;

import com.github.rodmotta.bid_service.config.RabbitConfig;
import com.github.rodmotta.bid_service.dto.response.AuctionResponse;
import com.github.rodmotta.bid_service.messaging.message.BidPlacedEventMessage;
import com.github.rodmotta.bid_service.persistence.entity.BidEntity;
import com.github.rodmotta.bid_service.security.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;

@Component
public class BidEventPublisher {
    private final Logger logger = LoggerFactory.getLogger(BidEventPublisher.class);
    private final RabbitTemplate rabbitTemplate;

    public BidEventPublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void placeBid(BidEntity bidEntity, AuctionResponse auctionResponse, User user, UUID previousBidderId, LocalDateTime placedAt) {
        logger.debug("Publishing bid placed event for auction ID {}. New bid ID: {}", bidEntity.getAuctionId(), bidEntity.getId());
        BidPlacedEventMessage message = new BidPlacedEventMessage(
                auctionResponse.id(),
                auctionResponse.title(),
                bidEntity.getAmount(),
                user.id(),
                previousBidderId,
                placedAt);

        rabbitTemplate.convertAndSend(RabbitConfig.BID_PLACED_EXCHANGE, "", message);
        logger.debug("Bid placed event published for auction ID {}.", bidEntity.getAuctionId());
    }
}
