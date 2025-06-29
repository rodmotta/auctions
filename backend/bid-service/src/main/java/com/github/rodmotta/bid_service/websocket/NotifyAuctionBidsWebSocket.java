package com.github.rodmotta.bid_service.websocket;

import com.github.rodmotta.bid_service.dto.response.BidResponse;
import com.github.rodmotta.bid_service.persistence.repository.BidRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

//fixme
@Component
@RequiredArgsConstructor
public class NotifyAuctionBidsWebSocket {
    private final BidRepository bidRepository;
    private final SimpMessagingTemplate messagingTemplate;

    public void execute(UUID auctionId) {
        List<BidResponse> topBids = bidRepository.findTop5ByAuctionIdOrderByAmountDesc(auctionId)
                .stream()
                .map(BidResponse::new)
                .toList();

        messagingTemplate.convertAndSend("/topic/auction/" + auctionId + "/bids", topBids);
    }
}
