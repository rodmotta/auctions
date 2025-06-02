package com.github.rodmotta.bid_service.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        // Habilita um broker de mensagens simples baseado em memória.
        // As mensagens enviadas para destinos que começam com "/topic" serão roteadas para os clientes inscritos.
        registry.enableSimpleBroker("/topic");
        // Define o prefixo para os endpoints de aplicação. Mensagens para "/app" serão roteadas para os métodos @MessageMapping.
        registry.setApplicationDestinationPrefixes("/app");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // Registra o endpoint "/ws" para a conexão WebSocket.
        // ".withSockJS()" habilita a compatibilidade com navegadores mais antigos que não suportam WebSocket nativamente.
        registry.addEndpoint("/ws")
                .setAllowedOriginPatterns("*");
                //.withSockJS();
    }
}
