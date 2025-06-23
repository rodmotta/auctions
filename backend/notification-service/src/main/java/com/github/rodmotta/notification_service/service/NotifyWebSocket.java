package com.github.rodmotta.notification_service.service;

import com.github.rodmotta.notification_service.dto.response.NotificationResponse;
import com.github.rodmotta.notification_service.persistence.repository.NotificationRepository;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class NotifyWebSocket {
    private final NotificationRepository notificationRepository;
    private final SimpMessagingTemplate messagingTemplate;

    public NotifyWebSocket(NotificationRepository notificationRepository, SimpMessagingTemplate messagingTemplate) {
        this.notificationRepository = notificationRepository;
        this.messagingTemplate = messagingTemplate;
    }

    public void notifyUserNotifications(UUID userId) {
        List<NotificationResponse> notifications = notificationRepository.findByNotifiedIdOrderByPlacedAtDesc(userId)
                .stream()
                .map(NotificationResponse::new)
                .toList();
        messagingTemplate.convertAndSend("/topic/notifications/" + userId, notifications);
    }
}
