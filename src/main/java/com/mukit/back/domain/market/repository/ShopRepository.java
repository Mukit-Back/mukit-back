package com.mukit.back.domain.market.repository;

import com.mukit.back.domain.market.entity.Shop;
import com.mukit.back.domain.market.entity.enums.MarketType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ShopRepository extends JpaRepository<Shop, Long> {

    List<Shop> findAllByMarket_MarketType(MarketType marketType);

    Optional<Shop> findByName(String name);
}
