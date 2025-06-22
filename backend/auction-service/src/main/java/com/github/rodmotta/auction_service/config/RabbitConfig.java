package com.github.rodmotta.auction_service.config;

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
    public static final String AUCTION_WINNER_QUEUE = "auction.auction.winner";
    public static final String BID_PLACED_QUEUE = "auction.bid.placed";

    @Bean
    public TopicExchange auctionEventsExchange() {
        return new TopicExchange(AUCTION_EVENTS_TOPIC);
    }

    @Bean
    public Queue winnerQueue() {
        return new Queue(AUCTION_WINNER_QUEUE, true);
    }

    @Bean
    public Binding bindWinnerQueueToExchange() {
        return BindingBuilder
                .bind(winnerQueue())
                .to(auctionEventsExchange())
                .with("auction.winner");
    }

    @Bean
    public Queue bidPlacedQueue() {
        return new Queue(BID_PLACED_QUEUE, true);
    }

    @Bean
    public Binding bindBidPlacedQueueToExchange() {
        return BindingBuilder
                .bind(bidPlacedQueue())
                .to(auctionEventsExchange())
                .with("bid.placed");
    }

    @Bean
    public Jackson2JsonMessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
