package com.github.rodmotta.bid_service.service;

import com.github.rodmotta.bid_service.client.AuctionClient;
import com.github.rodmotta.bid_service.dto.request.BidRequest;
import com.github.rodmotta.bid_service.dto.response.AuctionResponse;
import com.github.rodmotta.bid_service.dto.response.BidResponse;
import com.github.rodmotta.bid_service.exception.custom.ValidationException;
import com.github.rodmotta.bid_service.messaging.publisher.BidEventPublisher;
import com.github.rodmotta.bid_service.persistence.entity.BidEntity;
import com.github.rodmotta.bid_service.persistence.repository.BidRepository;
import com.github.rodmotta.bid_service.security.User;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class BidService {
    private final Logger logger = LoggerFactory.getLogger(BidService.class);
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
    public void placeBid(BidRequest bidRequest, User user) {
        logger.info("Attempting to place bid for auction ID {} by user {}. Bid amount: {}",
                bidRequest.auctionId(), user.id(), bidRequest.amount());
        LocalDateTime now = LocalDateTime.now();

        logger.debug("Retrieving auction {} details from AuctionClient for bid validation", bidRequest.auctionId());
        AuctionResponse auction = auctionClient.getAuctionById(bidRequest.auctionId());
        logger.debug("Successfully retrieved auction {} details", auction.id());

        validateAuction(user, auction, now);

        logger.debug("Found existing highest bid for auction {}", auction.id());
        Optional<BidEntity> currentHighestBid = bidRepository.findTopByAuctionIdOrderByAmountDesc(auction.id());

        BigDecimal baseAmount = currentHighestBid
                .map(BidEntity::getAmount)
                .orElse(auction.startingPrice());

        BigDecimal minimumRequiredBid = baseAmount.add(auction.minimumIncrement());
        logger.debug("Calculated minimum required bid for auction {}: {}", auction.id(), minimumRequiredBid);

        if (bidRequest.amount().compareTo(minimumRequiredBid) < 0) {
            logger.warn("Bid too low for auction ID {} by user {}. Bid amount: {}, Minimum required: {}",
                    bidRequest.auctionId(), user.id(), bidRequest.amount(), minimumRequiredBid);
            throw new ValidationException("Bid too low");
        }

        BidEntity newBid = bidRequest.toEntity();
        newBid.setCreatedAt(now);
        newBid.setUserId(user.id());
        newBid.setUserName(user.name());
        logger.debug("Saving new bid for auction ID {} by user {}. Bid amount: {}",
                newBid.getAuctionId(), newBid.getUserId(), newBid.getAmount());
        bidRepository.save(newBid);
        logger.info("Successfully placed bid for auction ID {} by user {}. Bid ID: {}, Amount: {}",
                newBid.getAuctionId(), newBid.getUserId(), newBid.getId(), newBid.getAmount());

        UUID previousBidderId = currentHighestBid
                .map(BidEntity::getUserId)
                .orElse(null);

        bidEventPublisher.placeBid(newBid, auction, user, previousBidderId, now);

        notifyTopBids(newBid.getAuctionId());
    }

    public List<BidResponse> getBidsByAuction(UUID auctionId) {
        logger.info("Attempting to retrieve top 5 bids for auction ID: {}", auctionId);
        List<BidResponse> topBids = bidRepository.findTop5ByAuctionIdOrderByAmountDesc(auctionId)
                .stream()
                .map(BidResponse::new)
                .toList();
        logger.info("Successfully retrieved {} top bids for auction ID: {}", topBids.size(), auctionId);
        return topBids;
    }

    private void validateAuction(User user, AuctionResponse auction, LocalDateTime now) {
        logger.debug("Starting auction validation for auction ID {} by user {}.", auction.id(), user.id());
        if (auction.ownerId().equals(user.id())) {
            throw new ValidationException("The auction owner cannot bid");
        }

        if (auction.endDate().isBefore(now)) {
            throw new ValidationException("Auction already closed");
        }
        logger.debug("Auction validation successful for auction ID {} by user {}.", auction.id(), user.id());
    }

    private void notifyTopBids(UUID auctionId) {
        logger.debug("Notifying top bids for auction ID {} via WebSocket.", auctionId);
        List<BidResponse> topBids = getBidsByAuction(auctionId);
        messagingTemplate.convertAndSend("/topic/auction/" + auctionId + "/bids", topBids);
        logger.debug("Top bids notification sent for auction ID {}.", auctionId);
    }
}
