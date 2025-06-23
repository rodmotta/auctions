package com.github.rodmotta.bid_service.messaging;

import com.github.rodmotta.bid_service.messaging.event.AuctionCompletedEvent;
import com.github.rodmotta.bid_service.service.AuctionWinnerNotifier;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import static com.github.rodmotta.bid_service.config.RabbitConfig.BID_AUCTION_FINALIZED_QUEUE;

@Component
public class RabbitConsumer {
    private final AuctionWinnerNotifier auctionWinnerNotifier;

    public RabbitConsumer(AuctionWinnerNotifier auctionWinnerNotifier) {
        this.auctionWinnerNotifier = auctionWinnerNotifier;
    }

    @RabbitListener(queues = BID_AUCTION_FINALIZED_QUEUE)
    public void handleAuctionCompletedEvent(AuctionCompletedEvent message) {
        auctionWinnerNotifier.execute(message.auctionId(), message.auctionTitle());
    }
}
