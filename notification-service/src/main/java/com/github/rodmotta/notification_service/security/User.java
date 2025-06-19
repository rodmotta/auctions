package com.github.rodmotta.notification_service.security;

import java.util.UUID;

public record User(
        UUID id,
        String name
) {
}
