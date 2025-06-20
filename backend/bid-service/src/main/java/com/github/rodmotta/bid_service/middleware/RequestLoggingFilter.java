package com.github.rodmotta.bid_service.middleware;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class RequestLoggingFilter implements Filter {
    private final Logger logger = LoggerFactory.getLogger(RequestLoggingFilter.class);

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        var httpRequest = (HttpServletRequest) request;
        if (httpRequest.getRequestURI().equals("/actuator/prometheus")) {
            filterChain.doFilter(request, response);
            return;
        }
        logger.info("Request: {} - {}", httpRequest.getMethod(), httpRequest.getRequestURI());
        filterChain.doFilter(request, response);
    }
}
