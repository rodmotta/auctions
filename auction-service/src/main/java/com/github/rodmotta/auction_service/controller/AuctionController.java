package com.github.rodmotta.auction_service.controller;

import com.github.rodmotta.auction_service.dto.request.AuctionRequest;
import com.github.rodmotta.auction_service.dto.response.AuctionResponse;
import com.github.rodmotta.auction_service.service.AuctionService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("auctions")
@CrossOrigin("*")
public class AuctionController {
    private final AuctionService auctionService;

    public AuctionController(AuctionService auctionService) {
        this.auctionService = auctionService;
    }

    @PostMapping
    @ResponseStatus(CREATED)
    public void create(@RequestBody AuctionRequest auctionRequest) {
        auctionService.create(auctionRequest);
    }

    @GetMapping
    @ResponseStatus(OK)
    public List<AuctionResponse> findAll() {
        return auctionService.findAll();
    }

    @GetMapping("{id}")
    @ResponseStatus(OK)
    public AuctionResponse findById(@PathVariable Long id) {
        return auctionService.findById(id);
    }
}
