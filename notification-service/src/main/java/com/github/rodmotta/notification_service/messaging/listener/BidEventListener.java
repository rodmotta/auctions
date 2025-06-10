package com.github.rodmotta.notification_service.messaging.listener;

import com.github.rodmotta.notification_service.config.RabbitConfig;
import com.github.rodmotta.notification_service.messaging.model.BidPlacedEventMessage;
import com.github.rodmotta.notification_service.service.NotificationService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class BidEventListener {
    private final NotificationService notificationService;

    public BidEventListener(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @RabbitListener(queues = RabbitConfig.BID_QUEUE)
    public void updateCurrentPriceEvent(BidPlacedEventMessage message) {
        notificationService.saveNotification(message);
    }
}
