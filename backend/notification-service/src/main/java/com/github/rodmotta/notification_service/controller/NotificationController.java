package com.github.rodmotta.notification_service.controller;

import com.github.rodmotta.notification_service.dto.response.NotificationResponse;
import com.github.rodmotta.notification_service.security.JWTUtils;
import com.github.rodmotta.notification_service.security.User;
import com.github.rodmotta.notification_service.usecase.GetNotificationsByUserId;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("notifications")
@RequiredArgsConstructor
public class NotificationController {
    private final GetNotificationsByUserId getNotificationsByUserId;

    @GetMapping
    public List<NotificationResponse> findByUserId(@AuthenticationPrincipal Jwt jwt) {
        User user = JWTUtils.extractUser(jwt);
        return getNotificationsByUserId.execute(user.id());
    }
}
