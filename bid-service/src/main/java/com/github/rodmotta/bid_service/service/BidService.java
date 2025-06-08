package com.github.rodmotta.bid_service.service;

import com.github.rodmotta.bid_service.client.AuctionClient;
import com.github.rodmotta.bid_service.dto.request.BidRequest;
import com.github.rodmotta.bid_service.dto.response.AuctionResponse;
import com.github.rodmotta.bid_service.dto.response.BidResponse;
import com.github.rodmotta.bid_service.dto.response.UserResponse;
import com.github.rodmotta.bid_service.exception.custom.ValidationException;
import com.github.rodmotta.bid_service.messaging.model.UpdateCurrentPriceEventMessage;
import com.github.rodmotta.bid_service.messaging.publisher.BidEventPublisher;
import com.github.rodmotta.bid_service.persistence.entity.BidEntity;
import com.github.rodmotta.bid_service.persistence.repository.BidRepository;
import jakarta.transaction.Transactional;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

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

        var auction = auctionClient.getAuctionById(bidRequest.auctionId());

        if (auction.ownerId().equals(user.id())) {
            throw new ValidationException("The auction owner cannot bid");
        }

        BigDecimal highestBid = getHighestBidByAuction(auction);

        if (bidRequest.amount().compareTo(highestBid) <= 0) {
            throw new ValidationException("Bid too low");
        }

        if (auction.endDate().isBefore(LocalDateTime.now())) {
            throw new ValidationException("Auction already closed");
        }

        BidEntity bidEntity = bidRequest.toEntity();
        bidEntity.setUserId(user.id());
        bidEntity.setUserName(user.name());
        bidRepository.save(bidEntity);
        bidEventPublisher.publishNewBid(new UpdateCurrentPriceEventMessage(bidRequest.auctionId(), bidRequest.amount()));
        notifyTopBids(bidEntity.getAuctionId());
    }

    private BigDecimal getHighestBidByAuction(AuctionResponse auction) {
        return bidRepository.findTopByAuctionIdOrderByAmountDesc(auction.id())
                .map(BidEntity::getAmount)
                .orElse(auction.startingPrice());
    }

    private void notifyTopBids(Long auctionId) {
        List<BidResponse> topBids = getBidsByAuction(auctionId);
        messagingTemplate.convertAndSend("/topic/auction/" + auctionId + "/bids", topBids);
    }

    public List<BidResponse> getBidsByAuction(Long auctionId) {
        return bidRepository.findTop5ByAuctionIdOrderByAmountDesc(auctionId)
                .stream()
                .map(BidResponse::new)
                .toList();
    }
}
