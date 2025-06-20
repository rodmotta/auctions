package com.github.rodmotta.notification_service.controller;

import com.github.rodmotta.notification_service.dto.response.NotificationResponse;
import com.github.rodmotta.notification_service.security.JWTUtils;
import com.github.rodmotta.notification_service.security.User;
import com.github.rodmotta.notification_service.service.NotificationService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("notifications")
public class NotificationController {
    private final NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @GetMapping
    public List<NotificationResponse> findByUserId(@AuthenticationPrincipal Jwt jwt) {
        User user = JWTUtils.extractUser(jwt);
        return notificationService.findByUserId(user.id());
    }
}
