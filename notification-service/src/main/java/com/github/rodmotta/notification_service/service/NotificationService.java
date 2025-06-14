package com.github.rodmotta.notification_service.service;

import com.github.rodmotta.notification_service.dto.response.NotificationResponse;
import com.github.rodmotta.notification_service.messaging.model.BidPlacedEventMessage;
import com.github.rodmotta.notification_service.persistence.entity.NotificationEntity;
import com.github.rodmotta.notification_service.persistence.repository.NotificationRepository;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationService {
    private final NotificationRepository notificationRepository;
    private final SimpMessagingTemplate messagingTemplate;

    public NotificationService(NotificationRepository notificationRepository, SimpMessagingTemplate messagingTemplate) {
        this.notificationRepository = notificationRepository;
        this.messagingTemplate = messagingTemplate;
    }

    public void saveNotification(BidPlacedEventMessage message) {
        if (message.bidderId().equals(message.previousBidderId())) return;

        NotificationEntity notification = new NotificationEntity(
                message.auctionId(),
                message.auctionTitle(),
                message.bidAmount(),
                message.previousBidderId(),
                message.placedAt());
        notificationRepository.save(notification);

        notify(notification.getNotifiedId());
    }

    public List<NotificationResponse> findByUserId(String userId) {
        return notificationRepository.findByNotifiedIdOrderByPlacedAtDesc(userId)
                .stream()
                .map(NotificationResponse::new)
                .toList();
    }

    private void notify(String userId) {
        List<NotificationResponse> notifications = findByUserId(userId);
        messagingTemplate.convertAndSend("/topic/notifications/" + userId, notifications);
    }
}
