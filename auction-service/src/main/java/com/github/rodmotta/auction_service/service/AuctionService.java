package com.github.rodmotta.auction_service.service;

import com.github.rodmotta.auction_service.security.User;
import com.github.rodmotta.auction_service.dto.request.AuctionRequest;
import com.github.rodmotta.auction_service.dto.response.AuctionResponse;
import com.github.rodmotta.auction_service.exception.custom.NotFoundException;
import com.github.rodmotta.auction_service.exception.custom.ValidationException;
import com.github.rodmotta.auction_service.persistence.entity.AuctionEntity;
import com.github.rodmotta.auction_service.persistence.repository.AuctionRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class AuctionService {
    private final AuctionRepository auctionRepository;

    public AuctionService(AuctionRepository auctionRepository) {
        this.auctionRepository = auctionRepository;
    }

    public void create(AuctionRequest auctionRequest, User owner) {
        if (auctionRequest.startDate().isAfter(auctionRequest.endDate())) {
            throw new ValidationException("Start date must be before end date");
        }

        AuctionEntity auction = auctionRequest.toEntity();
        auction.setOwnerId(owner.id());
        auction.setOwnerName(owner.name());

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

    public void updateCurrentPriceEvent(Long auctionId, BigDecimal amount) {
        AuctionEntity auctionEntity = auctionRepository.findById(auctionId)
                .orElseThrow(() -> new NotFoundException("Auction not found"));
        auctionEntity.setCurrentPrice(amount);
        auctionRepository.save(auctionEntity);
    }
}
