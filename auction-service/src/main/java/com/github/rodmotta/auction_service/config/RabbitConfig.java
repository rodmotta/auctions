package com.github.rodmotta.auction_service.config;

import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    public static final String AUCTION_FANOUT_EXCHANGE = "auction.fanout";
    public static final String AUCTION_DIRECT_EXCHANGE = "auction.direct";

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public FanoutExchange auctionFanoutExchange() {
        return new FanoutExchange(AUCTION_FANOUT_EXCHANGE);
    }

    @Bean
    public DirectExchange auctionDirectExchange() {
        return new DirectExchange(AUCTION_DIRECT_EXCHANGE);
    }
}
