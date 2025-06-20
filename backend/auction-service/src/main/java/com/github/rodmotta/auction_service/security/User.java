package com.github.rodmotta.auction_service.security;

import java.util.UUID;

public record User(
        UUID id,
        String name
) {
}
