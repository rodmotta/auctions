package com.github.rodmotta.bid_service.controller;

import com.github.rodmotta.bid_service.dto.request.BidRequest;
import com.github.rodmotta.bid_service.dto.response.BidResponse;
import com.github.rodmotta.bid_service.security.JWTUtils;
import com.github.rodmotta.bid_service.security.User;
import com.github.rodmotta.bid_service.service.BidService;
import jakarta.validation.Valid;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

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
    public void placeBid(@RequestBody @Valid BidRequest bidRequest,
                         @AuthenticationPrincipal Jwt jwt) {
        User user = JWTUtils.extractUser(jwt);
        bidService.placeBid(bidRequest, user);
    }

    @GetMapping("/auction/{auctionId}")
    @ResponseStatus(OK)
    public List<BidResponse> getBidsByAuction(@PathVariable UUID auctionId) {
        return bidService.getBidsByAuction(auctionId);
    }
}
