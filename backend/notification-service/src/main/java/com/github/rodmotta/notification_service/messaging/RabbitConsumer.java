package com.github.rodmotta.notification_service.messaging;

import com.github.rodmotta.notification_service.messaging.event.AuctionWinnerEvent;
import com.github.rodmotta.notification_service.messaging.event.BidPlacedEvent;
import com.github.rodmotta.notification_service.usecase.CreateOutbidNotificationUseCase;
import com.github.rodmotta.notification_service.usecase.CreateWinnerNotificationUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import static com.github.rodmotta.notification_service.config.RabbitConfig.NOTIFICATION_AUCTION_FINALIZED_QUEUE;
import static com.github.rodmotta.notification_service.config.RabbitConfig.NOTIFICATION_BID_PLACED_QUEUE;

@Component
@RequiredArgsConstructor
public class RabbitConsumer {
    private final CreateOutbidNotificationUseCase createOutbidNotificationUseCase;
    private final CreateWinnerNotificationUseCase createWinnerNotificationUseCase;

    @RabbitListener(queues = NOTIFICATION_BID_PLACED_QUEUE)
    public void updateCurrentPriceEvent(BidPlacedEvent message) {
        createOutbidNotificationUseCase.execute(message);
    }

    @RabbitListener(queues = NOTIFICATION_AUCTION_FINALIZED_QUEUE)
    public void saveWinnerNotification(AuctionWinnerEvent message) {
        createWinnerNotificationUseCase.execute(message);
    }
}
