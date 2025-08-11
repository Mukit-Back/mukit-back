package com.mukit.back.domain.shop.repository;

import com.mukit.back.domain.shop.entity.Shop;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShopRepository extends JpaRepository<Shop, Long> {
}
