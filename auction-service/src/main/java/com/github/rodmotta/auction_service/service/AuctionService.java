package com.github.rodmotta.auction_service.service;

import com.github.rodmotta.auction_service.dto.request.AuctionRequest;
import com.github.rodmotta.auction_service.dto.response.AuctionResponse;
import com.github.rodmotta.auction_service.repository.AuctionRepository;
import jakarta.transaction.Transactional;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuctionService {
    private final AuctionRepository auctionRepository;
    private final RabbitTemplate rabbitTemplate;

    public AuctionService(AuctionRepository auctionRepository, RabbitTemplate rabbitTemplate) {
        this.auctionRepository = auctionRepository;
        this.rabbitTemplate = rabbitTemplate;
    }

    @Transactional
    public void create(AuctionRequest auctionRequest) {
        //todo - validar datas
        var auctionEntity = auctionRequest.toEntity();
        var savedActionEntity = auctionRepository.save(auctionEntity);
        rabbitTemplate.convertAndSend("auction.fanout", savedActionEntity);
    }

    public List<AuctionResponse> findAll() {
        return auctionRepository.findAll()
                .stream()
                .map(AuctionResponse::new)
                .toList();
    }

    public AuctionResponse findById(Long id) {
        return auctionRepository.findById(id)
                .map(AuctionResponse::new)
                .orElseThrow(() -> new RuntimeException("Auction not found"));
    }
}
