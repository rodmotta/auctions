package com.github.rodmotta.auction_service.messaging.event;

import java.util.UUID;

public record AuctionWinnerEvent(
        UUID auctionId,
        UUID winnerId,
        String WinnerName
) {
}
