package com.github.rodmotta.auction_service.service;

import com.github.rodmotta.auction_service.client.UserClient;
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
    private final UserClient userClient;

    public AuctionService(AuctionRepository auctionRepository, UserClient userClient) {
        this.auctionRepository = auctionRepository;
        this.userClient = userClient;
    }

    public void create(AuctionRequest auctionRequest, Long userId) {
        if (LocalDateTime.now().isAfter(auctionRequest.endTime())) {
            throw new RuntimeException(); //todo - add error message
        }
        AuctionEntity auction = auctionRequest.toEntity();
        auction.setUserId(userId);
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
        String sellerName = getSellerName(auctionEntity); //fixme- realiza N buscas no userservice ao listar os leiloes
        return new AuctionResponse(auctionEntity, sellerName);
    }

    private String getSellerName(AuctionEntity auctionEntity) {
        return userClient.getUserName(auctionEntity.getUserId())
                .name();
    }

    public void updateHighestBid(Long auctionId, BigDecimal amount) {
        AuctionEntity auctionEntity = auctionRepository.findById(auctionId)
                .orElseThrow(); //fixme
        auctionEntity.setCurrentBid(amount);
        auctionRepository.save(auctionEntity);
    }
}
