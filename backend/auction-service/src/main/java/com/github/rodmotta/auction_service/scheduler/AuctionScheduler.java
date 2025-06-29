package com.github.rodmotta.auction_service.scheduler;

import com.github.rodmotta.auction_service.usecase.ActivatePendingAuctionsUseCase;
import com.github.rodmotta.auction_service.usecase.CompleteActiveAuctionsUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuctionScheduler {
    private final ActivatePendingAuctionsUseCase activatePendingAuctionsUseCase;
    private final CompleteActiveAuctionsUseCase completeActiveAuctionsUseCase;

    @Scheduled(fixedRate = 60000)
    public void activateScheduledAuctions() {
        activatePendingAuctionsUseCase.execute();
    }


    @Scheduled(fixedRate = 60000)
    public void concludeExpiredAuctions() {
        completeActiveAuctionsUseCase.execute();
    }
}
