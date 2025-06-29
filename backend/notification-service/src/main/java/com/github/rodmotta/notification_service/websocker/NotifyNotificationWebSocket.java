package com.github.rodmotta.notification_service.websocker;

import com.github.rodmotta.notification_service.dto.response.NotificationResponse;
import com.github.rodmotta.notification_service.persistence.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class NotifyNotificationWebSocket {
    private final NotificationRepository notificationRepository;
    private final SimpMessagingTemplate messagingTemplate;

    public void execute(UUID userId) {
        List<NotificationResponse> notifications = notificationRepository.findByNotifiedIdOrderByPlacedAtDesc(userId)
                .stream()
                .map(NotificationResponse::new)
                .toList();
        messagingTemplate.convertAndSend("/topic/notifications/" + userId, notifications);
    }
}
