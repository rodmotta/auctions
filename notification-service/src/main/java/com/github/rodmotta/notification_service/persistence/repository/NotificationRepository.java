package com.github.rodmotta.notification_service.persistence.repository;

import com.github.rodmotta.notification_service.persistence.entity.NotificationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository extends JpaRepository<NotificationEntity, Long> {
    List<NotificationEntity> findByNotifiedIdOrderByPlacedAtDesc(String userId);
}
