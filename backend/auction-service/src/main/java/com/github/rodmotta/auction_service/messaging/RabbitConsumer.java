package com.github.rodmotta.auction_service.messaging;

import com.github.rodmotta.auction_service.messaging.event.AuctionWinnerEvent;
import com.github.rodmotta.auction_service.messaging.event.BidPlacedEvent;
import com.github.rodmotta.auction_service.usecase.AssignAuctionWinnerUseCase;
import com.github.rodmotta.auction_service.usecase.UpdateAuctionBidUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import static com.github.rodmotta.auction_service.config.RabbitConfig.AUCTION_WINNER_QUEUE;
import static com.github.rodmotta.auction_service.config.RabbitConfig.BID_PLACED_QUEUE;

@Component
@RequiredArgsConstructor
public class RabbitConsumer {
    private final UpdateAuctionBidUseCase updateAuctionBidUseCase;
    private final AssignAuctionWinnerUseCase assignAuctionWinnerUseCase;

    @RabbitListener(queues = BID_PLACED_QUEUE)
    public void handleBidEvent(BidPlacedEvent message) {
        updateAuctionBidUseCase.execute(message.auctionId(), message.bidAmount());
    }

    @RabbitListener(queues = AUCTION_WINNER_QUEUE)
    public void handleAuctionWinnerEvent(AuctionWinnerEvent message) {
        assignAuctionWinnerUseCase.execute(message.auctionId(), message.winnerId(), message.WinnerName());
    }
}
