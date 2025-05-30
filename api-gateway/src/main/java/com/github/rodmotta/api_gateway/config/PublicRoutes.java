package com.github.rodmotta.api_gateway.config;

import java.util.Set;

public class PublicRoutes {

    private PublicRoutes() {
    }

    public static final Set<String> PUBLIC_GET_PATHS = Set.of(
            "/auctions",
            "/auctions/{auctionId}",
            "/bids/auction/{auctionId}"
    );

    public static final Set<String> PUBLIC_POST_PATHS = Set.of(
            "/users/register",
            "/users/login"
    );

    public static final Set<String> PUBLIC_PUT_PATHS = Set.of();

    public static final Set<String> PUBLIC_DELETE_PATHS = Set.of();
}
