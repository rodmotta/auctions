package com.github.rodmotta.bid_service.service;

import com.github.rodmotta.bid_service.client.AuctionClient;
import com.github.rodmotta.bid_service.dto.request.BidRequest;
import com.github.rodmotta.bid_service.dto.response.AuctionResponse;
import com.github.rodmotta.bid_service.dto.response.BidResponse;
import com.github.rodmotta.bid_service.messaging.RabbitProducer;
import com.github.rodmotta.bid_service.persistence.entity.BidEntity;
import com.github.rodmotta.bid_service.persistence.repository.BidRepository;
import com.github.rodmotta.bid_service.security.User;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class BidService {
    private final AuctionClient auctionClient;
    private final BidPlacementValidator bidPlacementValidator;
    private final BidRepository bidRepository;
    private final RabbitProducer rabbitProducer;
    private final NotifyWebSocket notifyWebSocket;

    public BidService(AuctionClient auctionClient, BidPlacementValidator bidPlacementValidator, BidRepository bidRepository, RabbitProducer rabbitProducer, NotifyWebSocket notifyWebSocket) {
        this.auctionClient = auctionClient;
        this.bidPlacementValidator = bidPlacementValidator;
        this.bidRepository = bidRepository;
        this.rabbitProducer = rabbitProducer;
        this.notifyWebSocket = notifyWebSocket;
    }

    @Transactional
    public void placeBid(BidRequest bidRequest, User user) {
        LocalDateTime now = LocalDateTime.now();

        AuctionResponse auction = auctionClient.getAuctionById(bidRequest.auctionId());

        BidEntity currentHighestBid = bidRepository.findTopByAuctionIdOrderByAmountDesc(auction.id())
                .orElse(null);

        bidPlacementValidator.execute(now, auction, user, currentHighestBid, bidRequest.amount());

        BidEntity newBid = bidRequest.toEntity();
        newBid.setCreatedAt(now);
        newBid.setUserId(user.id());
        newBid.setUserName(user.name());
        bidRepository.save(newBid);

        rabbitProducer.publishBidPlacedEvent(newBid, auction, user.id(), currentHighestBid, now);

        notifyWebSocket.execute(newBid.getAuctionId());
    }

    public List<BidResponse> getBidsByAuction(UUID auctionId) {
        return bidRepository.findTop5ByAuctionIdOrderByAmountDesc(auctionId)
                .stream()
                .map(BidResponse::new)
                .toList();
    }
}
