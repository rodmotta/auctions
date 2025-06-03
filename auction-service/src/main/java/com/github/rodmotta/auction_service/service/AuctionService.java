package com.github.rodmotta.auction_service.service;

import com.github.rodmotta.auction_service.dto.request.AuctionRequest;
import com.github.rodmotta.auction_service.dto.response.AuctionResponse;
import com.github.rodmotta.auction_service.dto.response.UserResponse;
import com.github.rodmotta.auction_service.entity.AuctionEntity;
import com.github.rodmotta.auction_service.exception.NotFoundException;
import com.github.rodmotta.auction_service.exception.ValidationException;
import com.github.rodmotta.auction_service.repository.AuctionRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class AuctionService {
    private final AuctionRepository auctionRepository;

    public AuctionService(AuctionRepository auctionRepository) {
        this.auctionRepository = auctionRepository;
    }

    public void create(AuctionRequest auctionRequest, UserResponse user) {
        if (LocalDateTime.now().isAfter(auctionRequest.endTime())) {
            throw new ValidationException("End time must be a future date");
        }
        AuctionEntity auction = auctionRequest.toEntity();
        auction.setUserId(user.id());
        auction.setUserName(user.name());
        auctionRepository.save(auction);
    }

    public List<AuctionResponse> findAll() {
        return auctionRepository.findAll()
                .stream()
                .map(AuctionResponse::new)
                .toList();
    }

    public AuctionResponse findById(Long id) {
        return auctionRepository.findById(id)
                .map(AuctionResponse::new)
                .orElseThrow(() -> new NotFoundException("Auction not found"));
    }

    public void updateHighestBid(Long auctionId, BigDecimal amount) {
        AuctionEntity auctionEntity = auctionRepository.findById(auctionId)
                .orElseThrow(() -> new NotFoundException("Auction not found"));
        auctionEntity.setCurrentBid(amount);
        auctionRepository.save(auctionEntity);
    }
}
