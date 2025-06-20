package com.github.rodmotta.notification_service.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {
    public static final String BID_PLACED_EXCHANGE = "bid.placed";
    public static final String BID_QUEUE = "notification-service-bid-queue";

    @Bean
    public FanoutExchange bidPlacedExchange() {
        return new FanoutExchange(BID_PLACED_EXCHANGE);
    }

    @Bean
    public Queue auctionServiceQueue() {
        return new Queue(BID_QUEUE);
    }

    @Bean
    public Binding bindingAuctionServiceQueue(Queue auctionServiceQueue, FanoutExchange bidPlacedExchange) {
        return BindingBuilder.bind(auctionServiceQueue).to(bidPlacedExchange);
    }

    @Bean
    public Jackson2JsonMessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
