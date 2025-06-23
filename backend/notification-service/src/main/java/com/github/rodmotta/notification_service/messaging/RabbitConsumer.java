package com.github.rodmotta.notification_service.messaging;

import com.github.rodmotta.notification_service.messaging.event.AuctionWinnerEvent;
import com.github.rodmotta.notification_service.messaging.event.BidPlacedEvent;
import com.github.rodmotta.notification_service.service.OutbidNotification;
import com.github.rodmotta.notification_service.service.WinnerNotification;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import static com.github.rodmotta.notification_service.config.RabbitConfig.NOTIFICATION_AUCTION_FINALIZED_QUEUE;
import static com.github.rodmotta.notification_service.config.RabbitConfig.NOTIFICATION_BID_PLACED_QUEUE;

@Component
public class RabbitConsumer {
    private final OutbidNotification outbidNotification;
    private final WinnerNotification winnerNotification;

    public RabbitConsumer(OutbidNotification outbidNotification, WinnerNotification winnerNotification) {
        this.outbidNotification = outbidNotification;
        this.winnerNotification = winnerNotification;
    }

    @RabbitListener(queues = NOTIFICATION_BID_PLACED_QUEUE)
    public void updateCurrentPriceEvent(BidPlacedEvent message) {
        outbidNotification.execute(message);
    }

    @RabbitListener(queues = NOTIFICATION_AUCTION_FINALIZED_QUEUE)
    public void saveWinnerNotification(AuctionWinnerEvent message) {
        winnerNotification.execute(message);
    }
}
