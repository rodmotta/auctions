package com.github.rodmotta.auction_service.messaging;

import com.github.rodmotta.auction_service.messaging.event.AuctionWinnerEvent;
import com.github.rodmotta.auction_service.messaging.event.BidPlacedEvent;
import com.github.rodmotta.auction_service.service.AuctionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import static com.github.rodmotta.auction_service.config.RabbitConfig.AUCTION_WINNER_QUEUE;
import static com.github.rodmotta.auction_service.config.RabbitConfig.BID_PLACED_QUEUE;

@Component
public class RabbitConsumer {
    private final Logger logger = LoggerFactory.getLogger(RabbitConsumer.class);
    private final AuctionService auctionService;

    public RabbitConsumer(AuctionService auctionService) {
        this.auctionService = auctionService;
    }

    @RabbitListener(queues = BID_PLACED_QUEUE)
    public void handleBidEvent(BidPlacedEvent message) {
        logger.info("Received message from '{}': '{}'.", BID_PLACED_QUEUE, message);
        auctionService.updateCurrentPriceEvent(message.auctionId(), message.bidAmount());
    }

    @RabbitListener(queues = AUCTION_WINNER_QUEUE)
    public void handleAuctionWinnerEvent(AuctionWinnerEvent message) {
        logger.info("Received message from '{}': '{}'.", AUCTION_WINNER_QUEUE, message);
        auctionService.updateAuctionWinnerEvent(message.auctionId(), message.winnerId(), message.WinnerName());
    }
}
