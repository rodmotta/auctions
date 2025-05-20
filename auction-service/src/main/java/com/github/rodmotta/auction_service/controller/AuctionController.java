package com.github.rodmotta.auction_service.controller;

import com.github.rodmotta.auction_service.dto.request.AuctionRequest;
import com.github.rodmotta.auction_service.dto.response.AuctionResponse;
import com.github.rodmotta.auction_service.service.AuctionService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("auctions")
public class AuctionController {
    private final AuctionService auctionService;

    public AuctionController(AuctionService auctionService) {
        this.auctionService = auctionService;
    }

    @PostMapping
    public void create(@RequestBody AuctionRequest auctionRequest) {
        auctionService.create(auctionRequest);
    }

    @GetMapping
    public List<AuctionResponse> findAll() {
        return auctionService.findAll();
    }

    @GetMapping("{id}")
    public AuctionResponse findById(@PathVariable Long id) {
        return auctionService.findById(id);
    }
}
