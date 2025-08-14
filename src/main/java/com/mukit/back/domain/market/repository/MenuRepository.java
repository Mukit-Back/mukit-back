package com.mukit.back.domain.market.repository;

import com.mukit.back.domain.market.entity.Menu;
import com.mukit.back.domain.market.entity.Shop;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MenuRepository extends JpaRepository<Menu, Long> {
    List<Menu> findAllByShop(Shop shop);
}
