package com.github.rodmotta.auction_service.messaging;

import com.github.rodmotta.auction_service.messaging.event.AuctionWinnerEvent;
import com.github.rodmotta.auction_service.messaging.event.BidPlacedEvent;
import com.github.rodmotta.auction_service.service.UpdateAuctionCurrentPrice;
import com.github.rodmotta.auction_service.service.UpdateAuctionWinner;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import static com.github.rodmotta.auction_service.config.RabbitConfig.AUCTION_WINNER_QUEUE;
import static com.github.rodmotta.auction_service.config.RabbitConfig.BID_PLACED_QUEUE;

@Component
public class RabbitConsumer {
    private final UpdateAuctionCurrentPrice updateAuctionCurrentPrice;
    private final UpdateAuctionWinner updateAuctionWinner;

    public RabbitConsumer(UpdateAuctionCurrentPrice updateAuctionCurrentPrice, UpdateAuctionWinner updateAuctionWinner) {
        this.updateAuctionCurrentPrice = updateAuctionCurrentPrice;
        this.updateAuctionWinner = updateAuctionWinner;
    }

    @RabbitListener(queues = BID_PLACED_QUEUE)
    public void handleBidEvent(BidPlacedEvent message) {
        updateAuctionCurrentPrice.execute(message.auctionId(), message.bidAmount());
    }

    @RabbitListener(queues = AUCTION_WINNER_QUEUE)
    public void handleAuctionWinnerEvent(AuctionWinnerEvent message) {
        updateAuctionWinner.execute(message.auctionId(), message.winnerId(), message.WinnerName());
    }
}
