package com.github.rodmotta.bid_service.service;

import com.github.rodmotta.bid_service.client.AuctionClient;
import com.github.rodmotta.bid_service.dto.request.BidRequest;
import com.github.rodmotta.bid_service.dto.response.BidResponse;
import com.github.rodmotta.bid_service.entity.BidEntity;
import com.github.rodmotta.bid_service.message.BidEventPublisher;
import com.github.rodmotta.bid_service.repository.BidRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class BidService {
    private final AuctionClient auctionClient;
    private final BidRepository bidRepository;
    private final BidEventPublisher bidEventPublisher;

    public BidService(AuctionClient auctionClient, BidRepository bidRepository, BidEventPublisher bidEventPublisher) {
        this.auctionClient = auctionClient;
        this.bidRepository = bidRepository;
        this.bidEventPublisher = bidEventPublisher;
    }

    @Transactional
    public void placeBid(BidRequest bidRequest, Long userId) {
        var auction = auctionClient.getAuctionById(bidRequest.auctionId());

        if (auction.endTime().isBefore(LocalDateTime.now())) {
            throw new IllegalStateException("Auction already closed");
        }

        BigDecimal highestBid = getHighestBidByAuction(auction.id());

        if (bidRequest.amount().compareTo(highestBid) <= 0) {
            throw new IllegalArgumentException("Bid too low");
        }

        BidEntity bidEntity = bidRequest.toEntity();
        bidEntity.setUserId(userId);
        bidRepository.save(bidEntity);
        bidEventPublisher.publishNewBid(bidRequest);
    }

    private BigDecimal getHighestBidByAuction(Long auctionId) {
        return bidRepository.findTopByAuctionIdOrderByAmountDesc(auctionId)
                .map(BidEntity::getAmount)
                .orElse(BigDecimal.ZERO);
    }

    public List<BidResponse> getBidsByAuction(Long auctionId) {
        return bidRepository.findTop5ByAuctionIdOrderByAmountDesc(auctionId)
                .stream()
                .map(BidResponse::new)
                .toList();
    }
}
