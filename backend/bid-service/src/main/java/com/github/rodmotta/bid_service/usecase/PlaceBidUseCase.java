package com.github.rodmotta.bid_service.usecase;

import com.github.rodmotta.bid_service.client.AuctionClient;
import com.github.rodmotta.bid_service.dto.request.BidRequest;
import com.github.rodmotta.bid_service.dto.response.AuctionResponse;
import com.github.rodmotta.bid_service.exception.custom.ValidationException;
import com.github.rodmotta.bid_service.messaging.RabbitProducer;
import com.github.rodmotta.bid_service.persistence.entity.BidEntity;
import com.github.rodmotta.bid_service.persistence.repository.BidRepository;
import com.github.rodmotta.bid_service.security.User;
import com.github.rodmotta.bid_service.websocket.NotifyAuctionBidsWebSocket;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class PlaceBidUseCase {
    private final AuctionClient auctionClient;
    private final BidRepository bidRepository;
    private final RabbitProducer rabbitProducer;
    private final NotifyAuctionBidsWebSocket notifyAuctionBidsWebSocket;

    @Transactional
    public void execute(BidRequest bidRequest, User user) {
        LocalDateTime now = LocalDateTime.now();

        AuctionResponse auction = auctionClient.getAuctionById(bidRequest.auctionId())
                .orElseThrow(() -> new ValidationException("Auction not found"));

        BidEntity currentHighestBid = bidRepository.findTopByAuctionIdOrderByAmountDesc(auction.id())
                .orElse(null);

        validateBid(now, auction, user, currentHighestBid, bidRequest.amount());

        BidEntity newBid = bidRequest.toEntity();
        newBid.setCreatedAt(now);
        newBid.setUserId(user.id());
        newBid.setUserName(user.name());
        bidRepository.save(newBid);

        rabbitProducer.publishBidPlacedEvent(newBid, auction, user.id(), currentHighestBid, now);

        notifyAuctionBidsWebSocket.execute(newBid.getAuctionId());
    }

    public void validateBid(LocalDateTime now, AuctionResponse auction, User user, BidEntity currentHighestBid, BigDecimal amount) {

        auction.assertValidToBid(now);

        if (auction.ownerId().equals(user.id())) {
            throw new ValidationException("The auction owner cannot bid");
        }

        BigDecimal minimumBidRequired = Objects.nonNull(currentHighestBid)
                ? currentHighestBid.getAmount()
                : auction.startingPrice();

        if (amount.compareTo(minimumBidRequired) < 0) {
            throw new ValidationException("Bid too low");
        }
    }
}
