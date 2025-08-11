package com.mukit.back.domain.shop.service;

import com.mukit.back.domain.market.entity.Market;
import com.mukit.back.domain.market.repository.MarketRepository;
import com.mukit.back.domain.shop.converter.ShopConverter;
import com.mukit.back.domain.shop.dto.request.ShopRequestDTO;
import com.mukit.back.domain.shop.dto.response.ShopResponseDTO;
import com.mukit.back.domain.shop.entity.Shop;
import com.mukit.back.domain.shop.exception.ShopErrorCode;
import com.mukit.back.domain.shop.exception.ShopException;
import com.mukit.back.domain.shop.repository.ShopRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ShopService {

    private final ShopRepository shopRepository;
    private final MarketRepository marketRepository;

    public ShopResponseDTO.ShopDetail getShopDetail(Long shopId) {
        Shop shop = shopRepository.findById(shopId)
                .orElseThrow(() -> new ShopException(ShopErrorCode.SHOP_NOT_FOUND));

        return ShopConverter.toShopDetailResponseDTO(shop);
    }

    public List<ShopResponseDTO.ShopDetail> getShopList() {
        return shopRepository.findAll()
                .stream().map(ShopConverter::toShopDetailResponseDTO)
                .toList();
    }

    public ShopResponseDTO.CreateShop createShop(ShopRequestDTO.CreateShop shopRequestDTO) {
        Market market = marketRepository.findById(shopRequestDTO.marketId())
                .orElseThrow(() -> new ShopException(ShopErrorCode.MARKET_NOT_FOUND));

        Shop shop = ShopConverter.toShop(shopRequestDTO, market);
        shopRepository.save(shop);

        return ShopConverter.toCreateShopResponse(shop);
    }

}
