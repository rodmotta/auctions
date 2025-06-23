package com.github.rodmotta.bid_service.service;

import com.github.rodmotta.bid_service.dto.response.AuctionResponse;
import com.github.rodmotta.bid_service.exception.custom.ValidationException;
import com.github.rodmotta.bid_service.persistence.entity.BidEntity;
import com.github.rodmotta.bid_service.security.User;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

@Service
public class BidPlacementValidator {

    public void execute(LocalDateTime now, AuctionResponse auction, User user, BidEntity currentHighestBid, BigDecimal amount) {
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
