package com.github.rodmotta.bid_service.messaging;

import com.github.rodmotta.bid_service.messaging.event.AuctionCompletedEvent;
import com.github.rodmotta.bid_service.service.BidService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import static com.github.rodmotta.bid_service.config.RabbitConfig.BID_AUCTION_FINALIZED_QUEUE;

@Component
public class RabbitConsumer {
    private final Logger logger = LoggerFactory.getLogger(RabbitConsumer.class);
    private final BidService bidService;

    public RabbitConsumer(BidService bidService) {
        this.bidService = bidService;
    }

    @RabbitListener(queues = BID_AUCTION_FINALIZED_QUEUE)
    public void handleAuctionCompletedEvent(AuctionCompletedEvent message) {
        logger.info("Received a completed auction ID: {}", message.auctionId());
        bidService.notifyHighestBid(message.auctionId(), message.auctionTitle());
    }
}
