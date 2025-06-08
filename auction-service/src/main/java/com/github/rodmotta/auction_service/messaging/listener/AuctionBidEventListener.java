package com.github.rodmotta.auction_service.messaging.listener;

import com.github.rodmotta.auction_service.config.RabbitConfig;
import com.github.rodmotta.auction_service.messaging.model.UpdateCurrentPriceEventMessage;
import com.github.rodmotta.auction_service.service.AuctionService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class AuctionBidEventListener {
    private final AuctionService auctionService;

    public AuctionBidEventListener(AuctionService auctionService) {
        this.auctionService = auctionService;
    }

    @RabbitListener(queues = RabbitConfig.AUCTION_BID_QUEUE)
    public void updateCurrentPriceEvent(UpdateCurrentPriceEventMessage message) {
        auctionService.updateCurrentPriceEvent(message.auctionId(), message.amount());
    }
}
