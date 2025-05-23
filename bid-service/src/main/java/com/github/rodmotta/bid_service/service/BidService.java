package com.github.rodmotta.bid_service.service;

import com.github.rodmotta.bid_service.client.AuctionClient;
import com.github.rodmotta.bid_service.dto.request.BidRequest;
import com.github.rodmotta.bid_service.dto.response.BidResponse;
import com.github.rodmotta.bid_service.entity.BidEntity;
import com.github.rodmotta.bid_service.repository.BidRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class BidService {
    private final AuctionClient auctionClient;
    private final BidRepository bidRepository;

    public BidService(AuctionClient auctionClient, BidRepository bidRepository) {
        this.auctionClient = auctionClient;
        this.bidRepository = bidRepository;
    }

    public void placeBid(BidRequest bidRequest) {
        var auction = auctionClient.getAuctionById(bidRequest.auctionId());

        if (auction.endTime().isBefore(LocalDateTime.now())) {
            throw new IllegalStateException("Auction already closed");
        }

        BigDecimal highestBid = getHighestBidByAuction(auction.id());
        if (highestBid.equals(BigDecimal.ZERO)) {
            highestBid = auction.currentBid();
        }

        if (bidRequest.amount().compareTo(highestBid) <= 0) {
            throw new IllegalArgumentException("Bid too low");
        }

        BidEntity bidEntity = new BidEntity(bidRequest.auctionId(), bidRequest.amount());
        bidRepository.save(bidEntity);
    }

    public List<BidResponse> getBidsByAuction(Long auctionId) {
        return null;
    }

    public BigDecimal getHighestBidByAuction(Long auctionId) {
        var highestBid = bidRepository.findTopByAuctionIdOrderByAmountDesc(auctionId)
                .orElse(new BidEntity(null, BigDecimal.ZERO));
        return highestBid.getAmount();
    }
}
