package com.github.rodmotta.bid_service.messaging.publisher;

import com.github.rodmotta.bid_service.config.RabbitConfig;
import com.github.rodmotta.bid_service.messaging.model.UpdateCurrentPriceEventMessage;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class BidEventPublisher {
    private final RabbitTemplate rabbitTemplate;

    public BidEventPublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void publishNewBid(UpdateCurrentPriceEventMessage message) {
        rabbitTemplate.convertAndSend(RabbitConfig.AUCTION_BID_QUEUE, message);
    }
}
