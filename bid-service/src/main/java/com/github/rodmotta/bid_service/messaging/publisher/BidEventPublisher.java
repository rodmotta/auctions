package com.github.rodmotta.bid_service.messaging.publisher;

import com.github.rodmotta.bid_service.config.RabbitConfig;
import com.github.rodmotta.bid_service.dto.response.AuctionResponse;
import com.github.rodmotta.bid_service.dto.response.UserResponse;
import com.github.rodmotta.bid_service.messaging.message.BidPlacedEventMessage;
import com.github.rodmotta.bid_service.persistence.entity.BidEntity;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class BidEventPublisher {
    private final RabbitTemplate rabbitTemplate;

    public BidEventPublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void placeBid(BidEntity bidEntity, AuctionResponse auctionResponse, UserResponse user, String previousBidderId, LocalDateTime placedAt) {
        BidPlacedEventMessage message = new BidPlacedEventMessage(
                auctionResponse.id(),
                auctionResponse.title(),
                bidEntity.getAmount(),
                user.id(),
                previousBidderId,
                placedAt);

        rabbitTemplate.convertAndSend(RabbitConfig.BID_PLACED_EXCHANGE, "", message);
    }
}
