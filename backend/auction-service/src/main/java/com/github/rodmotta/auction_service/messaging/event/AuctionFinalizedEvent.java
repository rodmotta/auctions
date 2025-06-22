package com.github.rodmotta.auction_service.messaging.event;

import java.util.UUID;

public record AuctionFinalizedEvent(
        UUID auctionId,
        String auctionTitle
) {
}
