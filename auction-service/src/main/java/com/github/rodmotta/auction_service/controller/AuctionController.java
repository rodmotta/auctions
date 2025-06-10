package com.github.rodmotta.auction_service.controller;

import com.github.rodmotta.auction_service.security.JwtUserExtractor;
import com.github.rodmotta.auction_service.security.User;
import com.github.rodmotta.auction_service.dto.request.AuctionRequest;
import com.github.rodmotta.auction_service.dto.response.AuctionResponse;
import com.github.rodmotta.auction_service.service.AuctionService;
import jakarta.validation.Valid;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public void createAuction(@RequestBody @Valid AuctionRequest auctionRequest,
                              @AuthenticationPrincipal Jwt jwt) {
        User loggedUser = JwtUserExtractor.from(jwt);
        auctionService.create(auctionRequest, loggedUser);
    }

    @GetMapping
    @ResponseStatus(OK)
    public List<AuctionResponse> getAllAuctions() {
        return auctionService.findAll();
    }

    @GetMapping("{id}")
    @ResponseStatus(OK)
    public AuctionResponse getAuctionById(@PathVariable Long id) {
        return auctionService.findById(id);
    }
}
