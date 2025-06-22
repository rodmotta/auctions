package com.github.rodmotta.bid_service.messaging.event;

import java.util.UUID;

public record AuctionCompletedEvent(
        UUID auctionId,
        String auctionTitle
) {
}
