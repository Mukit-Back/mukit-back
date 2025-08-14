package com.mukit.back.domain.market.repository;

import com.mukit.back.domain.market.entity.Shop;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShopRepository extends JpaRepository<Shop, Long> {
}
