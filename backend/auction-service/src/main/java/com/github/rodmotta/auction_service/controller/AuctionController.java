package com.github.rodmotta.auction_service.controller;

import com.github.rodmotta.auction_service.dto.request.AuctionRequest;
import com.github.rodmotta.auction_service.dto.response.AuctionResponse;
import com.github.rodmotta.auction_service.enums.AuctionStatus;
import com.github.rodmotta.auction_service.security.JWTUtils;
import com.github.rodmotta.auction_service.security.User;
import com.github.rodmotta.auction_service.usecase.CreateAuctionUseCase;
import com.github.rodmotta.auction_service.usecase.GetAuctionByIdUseCase;
import com.github.rodmotta.auction_service.usecase.GetAuctionsUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("auctions")
@RequiredArgsConstructor
public class AuctionController {
    private final CreateAuctionUseCase createAuctionUseCase;
    private final GetAuctionsUseCase getAuctionsUseCase;
    private final GetAuctionByIdUseCase getAuctionByIdUseCase;

    @PostMapping
    @ResponseStatus(CREATED)
    public void createAuction(@RequestBody @Valid AuctionRequest auctionRequest,
                              @AuthenticationPrincipal Jwt jwt) {
        User loggedUser = JWTUtils.extractUser(jwt);
        createAuctionUseCase.execute(auctionRequest, loggedUser);
    }

    @GetMapping
    @ResponseStatus(OK)
    public List<AuctionResponse> getAllAuctions(@RequestParam(defaultValue = "ACTIVE") AuctionStatus status) {
        return getAuctionsUseCase.execute(status);
    }

    @GetMapping("{id}")
    @ResponseStatus(OK)
    public AuctionResponse getAuctionById(@PathVariable UUID id) {
        return getAuctionByIdUseCase.execute(id);
    }
}
