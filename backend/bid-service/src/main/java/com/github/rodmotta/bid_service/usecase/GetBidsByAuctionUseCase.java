package com.github.rodmotta.bid_service.usecase;

import com.github.rodmotta.bid_service.dto.response.BidResponse;
import com.github.rodmotta.bid_service.persistence.repository.BidRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class GetBidsByAuctionUseCase {
    private final BidRepository bidRepository;

    public List<BidResponse> execute(UUID auctionId) {
        return bidRepository.findTop5ByAuctionIdOrderByAmountDesc(auctionId)
                .stream()
                .map(BidResponse::new)
                .toList();
    }
}
