package com.github.rodmotta.bid_service.service;

import com.github.rodmotta.bid_service.dto.response.BidResponse;
import com.github.rodmotta.bid_service.persistence.repository.BidRepository;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class NotifyWebSocket {
    private final BidRepository bidRepository;
    private final SimpMessagingTemplate messagingTemplate;

    public NotifyWebSocket(BidRepository bidRepository, SimpMessagingTemplate messagingTemplate) {
        this.bidRepository = bidRepository;
        this.messagingTemplate = messagingTemplate;
    }

    public void execute(UUID auctionId) {
        List<BidResponse> topBids = bidRepository.findTop5ByAuctionIdOrderByAmountDesc(auctionId)
                .stream()
                .map(BidResponse::new)
                .toList();

        messagingTemplate.convertAndSend("/topic/auction/" + auctionId + "/bids", topBids);
    }
}
