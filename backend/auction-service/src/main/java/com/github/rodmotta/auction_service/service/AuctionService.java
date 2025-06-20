package com.github.rodmotta.auction_service.service;

import com.github.rodmotta.auction_service.dto.request.AuctionRequest;
import com.github.rodmotta.auction_service.dto.response.AuctionResponse;
import com.github.rodmotta.auction_service.exception.custom.NotFoundException;
import com.github.rodmotta.auction_service.exception.custom.ValidationException;
import com.github.rodmotta.auction_service.persistence.entity.AuctionEntity;
import com.github.rodmotta.auction_service.persistence.repository.AuctionRepository;
import com.github.rodmotta.auction_service.security.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
public class AuctionService {
    private final Logger logger = LoggerFactory.getLogger(AuctionService.class);
    private final AuctionRepository auctionRepository;

    public AuctionService(AuctionRepository auctionRepository) {
        this.auctionRepository = auctionRepository;
    }

    public void create(AuctionRequest auctionRequest, User owner) {
        logger.info("Attempting to create auction for user {}. Auction details: {}", owner.id(), auctionRequest);

        if (auctionRequest.startDate().isAfter(auctionRequest.endDate())) {
            logger.warn("Validation failed to create auction for user {}: Start date {} is after end date {}",
                    owner.id(), auctionRequest.startDate(), auctionRequest.endDate());
            throw new ValidationException("Start date must be before end date");
        }

        AuctionEntity auction = auctionRequest.toEntity();
        auction.setOwnerId(owner.id());
        auction.setOwnerName(owner.name());

        AuctionEntity newAuction = auctionRepository.save(auction);
        logger.info("User {} created a new auction with id {}", owner.id(), newAuction.getId());
    }

    public List<AuctionResponse> findAll() {
        logger.info("Attempting to retrieve all auctions.");
        List<AuctionResponse> auctions = auctionRepository.findAll()
                .stream()
                .map(AuctionResponse::new)
                .toList();
        logger.info("Successfully retrieved {} auctions.", auctions.size());
        return auctions;
    }

    public AuctionResponse findById(UUID id) {
        logger.info("Attempting to find auction with ID: {}", id);
        AuctionResponse auction = auctionRepository.findById(id)
                .map(AuctionResponse::new)
                .orElseThrow(() -> {
                    logger.warn("Auction with ID {} not found.", id);
                    return new NotFoundException("Auction not found");
                });
        logger.info("Successfully retrieved auction by ID: {}", id);
        return auction;
    }

    public void updateCurrentPriceEvent(UUID auctionId, BigDecimal amount) {
        logger.info("Attempting to update current price for auction ID {}. New amount: {}", auctionId, amount);
        AuctionEntity auctionEntity = auctionRepository.findById(auctionId)
                .orElseThrow(() -> {
                    logger.warn("Failed to update price: Auction with ID {} not found.", auctionId);
                    return new NotFoundException("Auction not found");
                });
        Integer bidsCounter = auctionEntity.getBidsCounter();
        auctionEntity.setBidsCounter(bidsCounter + 1);
        auctionEntity.setCurrentPrice(amount);
        auctionRepository.save(auctionEntity);
        logger.info("Successfully updated current price for auction ID {} to {}. Bid count: {}",
                auctionId, amount, auctionEntity.getBidsCounter());
    }
}
