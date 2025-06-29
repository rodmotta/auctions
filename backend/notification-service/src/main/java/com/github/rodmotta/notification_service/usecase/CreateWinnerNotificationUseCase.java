package com.github.rodmotta.notification_service.usecase;

import com.github.rodmotta.notification_service.messaging.event.AuctionWinnerEvent;
import com.github.rodmotta.notification_service.persistence.entity.NotificationEntity;
import com.github.rodmotta.notification_service.persistence.repository.NotificationRepository;
import com.github.rodmotta.notification_service.websocker.NotifyNotificationWebSocket;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

import static com.github.rodmotta.notification_service.enums.NotificationTypeEnum.WINNER;

@Component
@RequiredArgsConstructor
public class CreateWinnerNotificationUseCase {
    private final NotificationRepository notificationRepository;
    private final NotifyNotificationWebSocket notifyNotificationWebSocket;

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

        notifyNotificationWebSocket.execute(notification.getNotifiedId());
    }
}
