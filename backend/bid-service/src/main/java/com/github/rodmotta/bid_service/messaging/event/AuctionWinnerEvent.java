package com.github.rodmotta.bid_service.messaging.event;

import java.util.UUID;

public record AuctionWinnerEvent(
        UUID auctionId,
        String auctionTitle,
        UUID winnerId,
        String WinnerName
) {
}
