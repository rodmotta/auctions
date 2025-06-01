package com.github.rodmotta.bid_service.message;

import com.github.rodmotta.bid_service.config.RabbitConfig;
import com.github.rodmotta.bid_service.dto.request.BidRequest;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class BidEventPublisher {
    private final RabbitTemplate rabbitTemplate;

    public BidEventPublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void publishNewBid(BidRequest bidRequest) {
        rabbitTemplate.convertAndSend(RabbitConfig.BID_QUEUE, bidRequest);
    }
}
