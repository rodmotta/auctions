package com.github.rodmotta.auction_service.service;

import com.github.rodmotta.auction_service.dto.request.AuctionRequest;
import com.github.rodmotta.auction_service.dto.response.AuctionResponse;
import com.github.rodmotta.auction_service.exception.custom.NotFoundException;
import com.github.rodmotta.auction_service.persistence.entity.AuctionEntity;
import com.github.rodmotta.auction_service.persistence.repository.AuctionRepository;
import com.github.rodmotta.auction_service.security.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

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
        AuctionEntity auction = auctionRequest.toEntity();
        auction.setOwnerId(owner.id());
        auction.setOwnerName(owner.name());

        AuctionEntity newAuction = auctionRepository.save(auction);
        logger.info("Auction created. id: {}, owner id: {}", newAuction.getId(), owner.id());
    }

    public List<AuctionResponse> findAll() {
        return auctionRepository.findAll()
                .stream()
                .map(AuctionResponse::new)
                .toList();
    }

    public AuctionResponse findById(UUID id) {
        return auctionRepository.findById(id)
                .map(AuctionResponse::new)
                .orElseThrow(() -> new NotFoundException("Auction not found"));
    }
}
