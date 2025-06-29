package com.github.rodmotta.bid_service.messaging;

import com.github.rodmotta.bid_service.messaging.event.AuctionCompletedEvent;
import com.github.rodmotta.bid_service.usecase.NotifyAuctionWinnerUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import static com.github.rodmotta.bid_service.config.RabbitConfig.BID_AUCTION_FINALIZED_QUEUE;

@Component
@RequiredArgsConstructor
public class RabbitConsumer {
    private final NotifyAuctionWinnerUseCase notifyAuctionWinnerUseCase;

    @RabbitListener(queues = BID_AUCTION_FINALIZED_QUEUE)
    public void handleAuctionCompletedEvent(AuctionCompletedEvent message) {
        notifyAuctionWinnerUseCase.execute(message.auctionId(), message.auctionTitle());
    }
}
