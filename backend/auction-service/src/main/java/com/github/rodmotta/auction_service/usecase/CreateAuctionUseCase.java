package com.github.rodmotta.auction_service.usecase;

import com.github.rodmotta.auction_service.dto.request.AuctionRequest;
import com.github.rodmotta.auction_service.persistence.entity.AuctionEntity;
import com.github.rodmotta.auction_service.persistence.repository.AuctionRepository;
import com.github.rodmotta.auction_service.security.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

@Component
@Log4j2
@RequiredArgsConstructor
public class CreateAuctionUseCase {
    private final AuctionRepository auctionRepository;

    public void execute(AuctionRequest auctionRequest, User owner) {
        AuctionEntity auction = auctionRequest.toEntity();
        auction.setOwnerId(owner.id());
        auction.setOwnerName(owner.name());

        AuctionEntity newAuction = auctionRepository.save(auction);
        log.info("Auction created. id: {}, owner id: {}", newAuction.getId(), owner.id());
    }
}
