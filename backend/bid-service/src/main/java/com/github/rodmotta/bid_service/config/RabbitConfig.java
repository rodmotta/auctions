package com.github.rodmotta.bid_service.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {
    public static final String AUCTION_EVENTS_TOPIC = "auction.events";
    public static final String BID_AUCTION_FINALIZED_QUEUE = "bid.auction.finalized";

    @Bean
    public TopicExchange auctionEventsExchange() {
        return new TopicExchange(AUCTION_EVENTS_TOPIC);
    }

    @Bean
    public Queue bidAuctionFinalizedQueue() {
        return new Queue(BID_AUCTION_FINALIZED_QUEUE);
    }

    @Bean
    public Binding auctionFinalizedBinding() {
        return BindingBuilder
                .bind(bidAuctionFinalizedQueue())
                .to(auctionEventsExchange())
                .with("auction.finalized");
    }

    @Bean
    public Jackson2JsonMessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}