package com.github.rodmotta.notification_service.messaging.event;

import java.util.UUID;

public record AuctionWinnerEvent(
        UUID auctionId,
        String auctionTitle,
        UUID winnerId,
        String WinnerName
) {
}
