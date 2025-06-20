package com.github.rodmotta.auction_service.messaging.listener;

import com.github.rodmotta.auction_service.messaging.message.BidPlacedEventMessage;
import com.github.rodmotta.auction_service.service.AuctionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import static com.github.rodmotta.auction_service.config.RabbitConfig.BID_PLACED_EXCHANGE;
import static com.github.rodmotta.auction_service.config.RabbitConfig.BID_QUEUE;

@Component
public class AuctionBidEventListener {
    private final Logger logger = LoggerFactory.getLogger(AuctionBidEventListener.class);
    private final AuctionService auctionService;

    public AuctionBidEventListener(AuctionService auctionService) {
        this.auctionService = auctionService;
    }

    @RabbitListener(queues = BID_QUEUE)
    public void handleBidEvent(BidPlacedEventMessage message) {
        logger.info("Received message from Exchange '{}', Queue '{}'. Message details: {}",
                BID_PLACED_EXCHANGE, BID_QUEUE, message);
        auctionService.updateCurrentPriceEvent(message.auctionId(), message.bidAmount());
    }
}
