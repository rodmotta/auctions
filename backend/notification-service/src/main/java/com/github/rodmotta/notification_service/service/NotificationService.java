package com.github.rodmotta.notification_service.service;

import com.github.rodmotta.notification_service.dto.response.NotificationResponse;
import com.github.rodmotta.notification_service.messaging.event.AuctionWinnerEvent;
import com.github.rodmotta.notification_service.messaging.event.BidPlacedEvent;
import com.github.rodmotta.notification_service.persistence.entity.NotificationEntity;
import com.github.rodmotta.notification_service.persistence.repository.NotificationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static com.github.rodmotta.notification_service.enums.NotificationTypeEnum.OUTBID;
import static com.github.rodmotta.notification_service.enums.NotificationTypeEnum.WINNER;

@Service
public class NotificationService {
    private final Logger logger = LoggerFactory.getLogger(NotificationService.class);
    private final NotificationRepository notificationRepository;
    private final SimpMessagingTemplate messagingTemplate;

    public NotificationService(NotificationRepository notificationRepository, SimpMessagingTemplate messagingTemplate) {
        this.notificationRepository = notificationRepository;
        this.messagingTemplate = messagingTemplate;
    }

    public void saveOutbidNotification(BidPlacedEvent message) {

        logger.info("Attempting to save notification for bid on auction ID {} by bidder {}. Previous bidder: {}",
                message.auctionId(), message.bidderId(), message.previousBidderId());
        if (message.bidderId().equals(message.previousBidderId())) {
            logger.debug("Skipping notification save: Current bidder {} is the same as previous bidder for auction ID {}.",
                    message.bidderId(), message.auctionId());
            return;
        }

        NotificationEntity notification = new NotificationEntity(
                message.auctionId(),
                message.auctionTitle(),
                message.bidAmount(),
                message.previousBidderId(),
                message.placedAt(),
                OUTBID);

        logger.info("Saving outbid notification for auction ID {}, winner ID: {}", notification.getNotifiedId(), notification.getAuctionId());
        notificationRepository.save(notification);

        notifyUserNotifications(notification.getNotifiedId());
    }

    public void saveWinnerNotification(AuctionWinnerEvent message) {

        NotificationEntity notification = new NotificationEntity(
                message.auctionId(),
                message.auctionTitle(),
                null,
                message.winnerId(),
                LocalDateTime.now(),
                WINNER);

        logger.info("Saving winner notification for auction ID {}, winner ID: {}", message.auctionId(), message.winnerId());
        notificationRepository.save(notification);

        notifyUserNotifications(notification.getNotifiedId());
    }

    public List<NotificationResponse> findByUserId(UUID userId) {
        return notificationRepository.findByNotifiedIdOrderByPlacedAtDesc(userId)
                .stream()
                .map(NotificationResponse::new)
                .toList();
    }

    private void notifyUserNotifications(UUID userId) {
        logger.debug("Notifying user {} via WebSocket for new notification.", userId);
        List<NotificationResponse> notifications = findByUserId(userId);
        messagingTemplate.convertAndSend("/topic/notifications/" + userId, notifications);
        logger.debug("WebSocket notification sent for user {}.", userId);
    }
}
