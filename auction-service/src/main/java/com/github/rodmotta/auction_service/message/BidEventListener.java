package com.github.rodmotta.auction_service.message;

import com.github.rodmotta.auction_service.config.RabbitConfig;
import com.github.rodmotta.auction_service.dto.request.NewBidEvent;
import com.github.rodmotta.auction_service.service.AuctionService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class BidEventListener {
    private final AuctionService auctionService;

    public BidEventListener(AuctionService auctionService) {
        this.auctionService = auctionService;
    }

    @RabbitListener(queues = RabbitConfig.BID_QUEUE)
    public void handleNewBid(NewBidEvent bid) {
        auctionService.updateHighestBid(bid.auctionId(), bid.amount());
    }
}
