package com.github.rodmotta.auction_service.usecase;

import com.github.rodmotta.auction_service.dto.response.AuctionResponse;
import com.github.rodmotta.auction_service.enums.AuctionStatus;
import com.github.rodmotta.auction_service.persistence.entity.AuctionEntity;
import com.github.rodmotta.auction_service.persistence.repository.AuctionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class GetAuctionsUseCase {
    private final AuctionRepository auctionRepository;

    public List<AuctionResponse> execute(AuctionStatus status) {
        Example<AuctionEntity> example = Example.of(new AuctionEntity(status));

        return auctionRepository.findAll(example)
                .stream()
                .map(AuctionResponse::new)
                .toList();
    }
}
