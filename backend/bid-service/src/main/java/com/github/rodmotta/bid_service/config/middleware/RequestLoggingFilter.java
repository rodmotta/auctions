package com.github.rodmotta.bid_service.config.middleware;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Log4j2
@Component
public class RequestLoggingFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        var httpRequest = (HttpServletRequest) request;
        if (httpRequest.getRequestURI().equals("/actuator/prometheus")) {
            filterChain.doFilter(request, response);
            return;
        }
        log.info("Request: {} - {}", httpRequest.getMethod(), httpRequest.getRequestURI());
        filterChain.doFilter(request, response);
    }
}
