package com.github.rodmotta.bid_service.service;

import com.github.rodmotta.bid_service.client.AuctionClient;
import com.github.rodmotta.bid_service.dto.request.BidRequest;
import com.github.rodmotta.bid_service.dto.response.AuctionResponse;
import com.github.rodmotta.bid_service.dto.response.BidResponse;
import com.github.rodmotta.bid_service.dto.response.UserResponse;
import com.github.rodmotta.bid_service.exception.custom.ValidationException;
import com.github.rodmotta.bid_service.messaging.publisher.BidEventPublisher;
import com.github.rodmotta.bid_service.persistence.entity.BidEntity;
import com.github.rodmotta.bid_service.persistence.repository.BidRepository;
import jakarta.transaction.Transactional;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class BidService {
    private final AuctionClient auctionClient;
    private final BidRepository bidRepository;
    private final BidEventPublisher bidEventPublisher;
    private final SimpMessagingTemplate messagingTemplate;

    public BidService(AuctionClient auctionClient, BidRepository bidRepository, BidEventPublisher bidEventPublisher, SimpMessagingTemplate messagingTemplate) {
        this.auctionClient = auctionClient;
        this.bidRepository = bidRepository;
        this.bidEventPublisher = bidEventPublisher;
        this.messagingTemplate = messagingTemplate;
    }

    @Transactional
    public void placeBid(BidRequest bidRequest, UserResponse user) {
        LocalDateTime now = LocalDateTime.now();

        AuctionResponse auction = auctionClient.getAuctionById(bidRequest.auctionId());
        validateAuction(user, auction, now);

        Optional<BidEntity> currentHighestBid = bidRepository.findTopByAuctionIdOrderByAmountDesc(auction.id());

        BigDecimal baseAmount = currentHighestBid
                .map(BidEntity::getAmount)
                .orElse(auction.startingPrice());

        BigDecimal minimumRequiredBid = baseAmount.add(auction.minimumIncrement());

        if (bidRequest.amount().compareTo(minimumRequiredBid) < 0) {
            throw new ValidationException("Bid too low");
        }

        BidEntity newBid = bidRequest.toEntity();
        newBid.setCreatedAt(now);
        newBid.setUserId(user.id());
        newBid.setUserName(user.name());
        bidRepository.save(newBid);

        String previousBidderId = currentHighestBid
                .map(BidEntity::getUserId)
                .orElse(null);

        bidEventPublisher.placeBid(newBid, auction, user, previousBidderId, now);

        notifyTopBids(newBid.getAuctionId());
    }

    public List<BidResponse> getBidsByAuction(Long auctionId) {
        return bidRepository.findTop5ByAuctionIdOrderByAmountDesc(auctionId)
                .stream()
                .map(BidResponse::new)
                .toList();
    }

    private void validateAuction(UserResponse user, AuctionResponse auction, LocalDateTime now) {
        if (auction.ownerId().equals(user.id())) {
            throw new ValidationException("The auction owner cannot bid");
        }

        if (auction.endDate().isBefore(now)) {
            throw new ValidationException("Auction already closed");
        }
    }

    private void notifyTopBids(Long auctionId) {
        List<BidResponse> topBids = getBidsByAuction(auctionId);
        messagingTemplate.convertAndSend("/topic/auction/" + auctionId + "/bids", topBids);
    }
}
