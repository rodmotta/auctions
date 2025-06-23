package com.github.rodmotta.notification_service.service;

import com.github.rodmotta.notification_service.dto.response.NotificationResponse;
import com.github.rodmotta.notification_service.persistence.repository.NotificationRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class NotificationService {
    private final NotificationRepository notificationRepository;

    public NotificationService(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    public List<NotificationResponse> findByUserId(UUID userId) {
        return notificationRepository.findByNotifiedIdOrderByPlacedAtDesc(userId)
                .stream()
                .map(NotificationResponse::new)
                .toList();
    }
}
