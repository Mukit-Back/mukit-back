package com.mukit.back.domain.market.service;

import com.mukit.back.domain.market.entity.Market;
import com.mukit.back.domain.market.entity.enums.MarketType;
import com.mukit.back.domain.market.repository.MarketRepository;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class MarketService {
    private final MarketRepository marketRepository;

    public void createMarket() {
        Market tonginMarket = Market.builder()
                .name("TONGIN")
                .marketType(MarketType.TONGIN)
                .build();

        Market mangwonMarket = Market.builder()
                .name("MANGWON")
                .marketType(MarketType.MANGWON)
                .build();

        Market namdaemunMarket = Market.builder()
                .name("NAMDAEMUN")
                .marketType(MarketType.NAMDAEMUN)
                .build();

        marketRepository.save(tonginMarket);
        marketRepository.save(mangwonMarket);
        marketRepository.save(namdaemunMarket);
    }
}
