package com.github.rodmotta.notification_service.service;

import com.github.rodmotta.notification_service.messaging.event.BidPlacedEvent;
import com.github.rodmotta.notification_service.persistence.entity.NotificationEntity;
import com.github.rodmotta.notification_service.persistence.repository.NotificationRepository;
import org.springframework.stereotype.Service;

import static com.github.rodmotta.notification_service.enums.NotificationTypeEnum.OUTBID;

@Service
public class OutbidNotification {
    private final NotificationRepository notificationRepository;
    private final NotifyWebSocket notifyWebSocket;

    public OutbidNotification(NotificationRepository notificationRepository, NotifyWebSocket notifyWebSocket) {
        this.notificationRepository = notificationRepository;
        this.notifyWebSocket = notifyWebSocket;
    }

    public void execute(BidPlacedEvent message) {

        if (message.bidderId().equals(message.previousBidderId())) {
            return;
        }

        NotificationEntity notification = new NotificationEntity(
                message.auctionId(),
                message.auctionTitle(),
                message.bidAmount(),
                message.previousBidderId(),
                message.placedAt(),
                OUTBID);

        notificationRepository.save(notification);

        notifyWebSocket.notifyUserNotifications(notification.getNotifiedId());
    }
}
