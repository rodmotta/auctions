package com.github.rodmotta.notification_service.usecase;

import com.github.rodmotta.notification_service.dto.response.NotificationResponse;
import com.github.rodmotta.notification_service.persistence.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class GetNotificationsByUserId {
    private final NotificationRepository notificationRepository;

    public List<NotificationResponse> execute(UUID userId) {
        return notificationRepository.findByNotifiedIdOrderByPlacedAtDesc(userId)
                .stream()
                .map(NotificationResponse::new)
                .toList();
    }
}
