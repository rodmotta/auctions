package com.github.rodmotta.api_gateway.middleware;

import com.github.rodmotta.api_gateway.service.JwtService;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.http.HttpMethod;
import org.springframework.http.server.PathContainer;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.util.pattern.PathPatternParser;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.Set;

import static com.github.rodmotta.api_gateway.config.PublicRoutes.*;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpMethod.*;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@Component
public class AuthenticationFilter implements GlobalFilter {

    private final JwtService jwtService;

    public AuthenticationFilter(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();

        if (isPublicPath(request)) {
            return chain.filter(exchange);
        }

        String authHeader = request.getHeaders().getFirst(AUTHORIZATION);

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return unauthorized(exchange);
        }

        String token = authHeader.substring(7);
        if (!jwtService.isTokenValid(token)) {
            return unauthorized(exchange);
        }

        String userId = jwtService.getSubject(token);

        ServerHttpRequest mutatedRequest = request.mutate()
                .header("x-user-id", userId)
                .build();

        ServerWebExchange mutatedExchange = exchange.mutate()
                .request(mutatedRequest)
                .build();

        return chain.filter(mutatedExchange);
    }

    private boolean isPublicPath(ServerHttpRequest request) {
        String path = request.getPath().toString();
        HttpMethod method = request.getMethod();

        var pathsPerMethod = Map.of(
                GET, PUBLIC_GET_PATHS,
                POST, PUBLIC_POST_PATHS,
                PUT, PUBLIC_PUT_PATHS,
                DELETE, PUBLIC_DELETE_PATHS
        );

        Set<String> publicPaths = pathsPerMethod.getOrDefault(method, Set.of());

        PathPatternParser pathPatternParser = new PathPatternParser();

        return publicPaths.stream()
                .anyMatch(publicPath -> pathPatternParser.parse(publicPath).matches(PathContainer.parsePath(path)));
    }

    private Mono<Void> unauthorized(ServerWebExchange exchange) {
        exchange.getResponse().setStatusCode(UNAUTHORIZED);
        return exchange.getResponse().setComplete();
    }
}
