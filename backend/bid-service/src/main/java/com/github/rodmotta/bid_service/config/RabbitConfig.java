package com.github.rodmotta.bid_service.config;

import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {
    public static final String BID_PLACED_EXCHANGE = "bid.placed";

    @Bean
    public FanoutExchange bidPlacedExchange() {
        return new FanoutExchange(BID_PLACED_EXCHANGE);
    }

    @Bean
    public Jackson2JsonMessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
