package com.github.rodmotta.notification_service.usecase;

import com.github.rodmotta.notification_service.messaging.event.BidPlacedEvent;
import com.github.rodmotta.notification_service.persistence.entity.NotificationEntity;
import com.github.rodmotta.notification_service.persistence.repository.NotificationRepository;
import com.github.rodmotta.notification_service.websocker.NotifyNotificationWebSocket;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static com.github.rodmotta.notification_service.enums.NotificationTypeEnum.OUTBID;

@Component
@RequiredArgsConstructor
public class CreateOutbidNotificationUseCase {
    private final NotificationRepository notificationRepository;
    private final NotifyNotificationWebSocket notifyNotificationWebSocket;

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

        notifyNotificationWebSocket.execute(notification.getNotifiedId());
    }
}
