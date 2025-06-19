package com.github.rodmotta.notification_service.service;

import com.github.rodmotta.notification_service.dto.response.NotificationResponse;
import com.github.rodmotta.notification_service.messaging.model.BidPlacedEventMessage;
import com.github.rodmotta.notification_service.persistence.entity.NotificationEntity;
import com.github.rodmotta.notification_service.persistence.repository.NotificationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class NotificationService {
    private final Logger logger = LoggerFactory.getLogger(NotificationService.class);
    private final NotificationRepository notificationRepository;
    private final SimpMessagingTemplate messagingTemplate;

    public NotificationService(NotificationRepository notificationRepository, SimpMessagingTemplate messagingTemplate) {
        this.notificationRepository = notificationRepository;
        this.messagingTemplate = messagingTemplate;
    }

    public void saveNotification(BidPlacedEventMessage message) {

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
                message.placedAt());
        logger.debug("Saving new notification for user {}. Auction ID: {}, Amount: {}",
                notification.getNotifiedId(), notification.getAuctionId(), notification.getBidAmount());
        notificationRepository.save(notification);
        logger.info("Successfully saved notification for user {}. Notification ID: {}, Auction ID: {}",
                notification.getNotifiedId(), notification.getId(), notification.getAuctionId());

        notify(notification.getNotifiedId());
    }

    public List<NotificationResponse> findByUserId(UUID userId) {
        return notificationRepository.findByNotifiedIdOrderByPlacedAtDesc(userId)
                .stream()
                .map(NotificationResponse::new)
                .toList();
    }

    private void notify(UUID userId) {
        logger.debug("Notifying user {} via WebSocket for new notification.", userId);
        List<NotificationResponse> notifications = findByUserId(userId);
        messagingTemplate.convertAndSend("/topic/notifications/" + userId, notifications);
        logger.debug("WebSocket notification sent for user {}.", userId);
    }
}
