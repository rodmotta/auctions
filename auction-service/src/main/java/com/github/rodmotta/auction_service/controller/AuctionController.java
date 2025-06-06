package com.github.rodmotta.auction_service.controller;

import com.github.rodmotta.auction_service.dto.request.AuctionRequest;
import com.github.rodmotta.auction_service.dto.response.AuctionResponse;
import com.github.rodmotta.auction_service.dto.response.UserResponse;
import com.github.rodmotta.auction_service.service.AuctionService;
import jakarta.validation.Valid;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("auctions")
public class AuctionController {
    private final AuctionService auctionService;

    public AuctionController(AuctionService auctionService) {
        this.auctionService = auctionService;
    }

    @PostMapping
    @ResponseStatus(CREATED)
    public void create(@RequestBody @Valid AuctionRequest auctionRequest,
                       @AuthenticationPrincipal Jwt jwt) {
        var user = new UserResponse(jwt);
        auctionService.create(auctionRequest, user);
    }

    @GetMapping
    @ResponseStatus(OK)
    public List<AuctionResponse> findAll(@RequestHeader Map<String, String> headers) {
        return auctionService.findAll();
    }

    @GetMapping("{id}")
    @ResponseStatus(OK)
    public AuctionResponse findById(@PathVariable Long id) {
        return auctionService.findById(id);
    }
}
