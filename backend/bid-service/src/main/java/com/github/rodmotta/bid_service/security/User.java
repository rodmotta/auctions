package com.github.rodmotta.bid_service.security;

import java.util.UUID;

public record User(
        UUID id,
        String name
) {
}
