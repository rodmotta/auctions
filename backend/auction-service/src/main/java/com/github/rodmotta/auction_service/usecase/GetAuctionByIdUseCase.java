package com.github.rodmotta.auction_service.usecase;

import com.github.rodmotta.auction_service.dto.response.AuctionResponse;
import com.github.rodmotta.auction_service.exception.custom.NotFoundException;
import com.github.rodmotta.auction_service.persistence.repository.AuctionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class GetAuctionByIdUseCase {
    private final AuctionRepository auctionRepository;

    public AuctionResponse execute(UUID id) {
        return auctionRepository.findById(id)
                .map(AuctionResponse::new)
                .orElseThrow(() -> new NotFoundException("Auction not found"));
    }
}
