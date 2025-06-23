package com.github.rodmotta.notification_service.config;

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
    public static final String NOTIFICATION_BID_PLACED_QUEUE = "notification.bid.placed";
    public static final String NOTIFICATION_AUCTION_FINALIZED_QUEUE = "notification.auction.finalized";

    @Bean
    public Queue bidPlacedQueue() {
        return new Queue(NOTIFICATION_BID_PLACED_QUEUE);
    }

    @Bean
    public Binding bidPlacedBinding() {
        return BindingBuilder
                .bind(bidPlacedQueue())
                .to(auctionEventsExchange())
                .with("bid.placed");
    }

    @Bean
    public TopicExchange auctionEventsExchange() {
        return new TopicExchange(AUCTION_EVENTS_TOPIC);
    }

    @Bean
    public Queue notificationAuctionFinalizedQueue() {
        return new Queue(NOTIFICATION_AUCTION_FINALIZED_QUEUE);
    }

    @Bean
    public Binding auctionFinalizedBinding() {
        return BindingBuilder
                .bind(notificationAuctionFinalizedQueue())
                .to(auctionEventsExchange())
                .with("auction.winner");
    }

    @Bean
    public Jackson2JsonMessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
