package com.github.rodmotta.auction_service.service;

import com.github.rodmotta.auction_service.client.BidClient;
import com.github.rodmotta.auction_service.dto.request.AuctionRequest;
import com.github.rodmotta.auction_service.dto.response.AuctionResponse;
import com.github.rodmotta.auction_service.entity.AuctionEntity;
import com.github.rodmotta.auction_service.repository.AuctionRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class AuctionService {
    private final AuctionRepository auctionRepository;
    private final BidClient bidClient;

    public AuctionService(AuctionRepository auctionRepository, BidClient bidClient) {
        this.auctionRepository = auctionRepository;
        this.bidClient = bidClient;
    }

    public void create(AuctionRequest auctionRequest) {
        if (LocalDateTime.now().isAfter(auctionRequest.endTime())) {
            throw new RuntimeException(); //todo - add error message
        }
        var auction = auctionRequest.toEntity();
        auctionRepository.save(auction);
    }

    public List<AuctionResponse> findAll() {
        return auctionRepository.findAll()
                .stream()
                .map(this::mapAuctionResponse)
                .toList();
    }

    public AuctionResponse findById(Long id) {
        return auctionRepository.findById(id)
                .map(this::mapAuctionResponse)
                .orElseThrow(() -> new RuntimeException("Auction not found"));
    }

    private AuctionResponse mapAuctionResponse(AuctionEntity auctionEntity) {
        BigDecimal currentBid = BigDecimal.ZERO;

        try {
            currentBid = bidClient.getHighestBidByAuctionId(auctionEntity.getId());
        } catch (Exception ignore) {}

        if (currentBid.equals(BigDecimal.ZERO)) {
            return new AuctionResponse(auctionEntity);
        }
        return new AuctionResponse(auctionEntity, currentBid);
    }
}
