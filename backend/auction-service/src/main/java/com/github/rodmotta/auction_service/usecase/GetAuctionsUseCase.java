package com.github.rodmotta.auction_service.usecase;

import com.github.rodmotta.auction_service.dto.response.AuctionResponse;
import com.github.rodmotta.auction_service.persistence.repository.AuctionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class GetAuctionsUseCase {
    private final AuctionRepository auctionRepository;

    public List<AuctionResponse> execute() {
        return auctionRepository.findAll()
                .stream()
                .map(AuctionResponse::new)
                .toList();
    }
}
