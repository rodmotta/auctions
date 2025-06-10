package com.github.rodmotta.auction_service.messaging.listener;

import com.github.rodmotta.auction_service.config.RabbitConfig;
import com.github.rodmotta.auction_service.messaging.message.BidPlacedEventMessage;
import com.github.rodmotta.auction_service.service.AuctionService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class AuctionBidEventListener {
    private final AuctionService auctionService;

    public AuctionBidEventListener(AuctionService auctionService) {
        this.auctionService = auctionService;
    }

    @RabbitListener(queues = RabbitConfig.BID_QUEUE)
    public void handleBidEvent(BidPlacedEventMessage message) {
        auctionService.updateCurrentPriceEvent(message.auctionId(), message.bidAmount());
    }
}
