package com.github.rodmotta.bid_service.controller;

import com.github.rodmotta.bid_service.dto.request.BidRequest;
import com.github.rodmotta.bid_service.dto.response.BidResponse;
import com.github.rodmotta.bid_service.service.BidService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("bids")
public class BidController {
    private final BidService bidService;

    public BidController(BidService bidService) {
        this.bidService = bidService;
    }

    @PostMapping
    @ResponseStatus(CREATED)
    public void placeBid(@RequestBody BidRequest bidRequest, @RequestHeader("x-user-id") Long userId) {
        bidService.placeBid(bidRequest, userId);
    }

    @GetMapping("/auction/{auctionId}")
    @ResponseStatus(OK)
    public List<BidResponse> getBidsByAuction(@PathVariable Long auctionId) {
        return bidService.getBidsByAuction(auctionId);
    }
}
