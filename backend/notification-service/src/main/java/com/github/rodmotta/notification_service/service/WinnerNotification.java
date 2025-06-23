package com.github.rodmotta.notification_service.service;

import com.github.rodmotta.notification_service.messaging.event.AuctionWinnerEvent;
import com.github.rodmotta.notification_service.persistence.entity.NotificationEntity;
import com.github.rodmotta.notification_service.persistence.repository.NotificationRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import static com.github.rodmotta.notification_service.enums.NotificationTypeEnum.WINNER;

@Service
public class WinnerNotification {
    private final NotificationRepository notificationRepository;
    private final NotifyWebSocket notifyWebSocket;

    public WinnerNotification(NotificationRepository notificationRepository, NotifyWebSocket notifyWebSocket) {
        this.notificationRepository = notificationRepository;
        this.notifyWebSocket = notifyWebSocket;
    }

    public void execute(AuctionWinnerEvent message) {

        NotificationEntity notification = new NotificationEntity(
                message.auctionId(),
                message.auctionTitle(),
                null,
                message.winnerId(),
                LocalDateTime.now(),
                WINNER);

//        logger.info("Saving winner notification for auction ID {}, winner ID: {}", message.auctionId(), message.winnerId());
        notificationRepository.save(notification);

        notifyWebSocket.notifyUserNotifications(notification.getNotifiedId());
    }
}
