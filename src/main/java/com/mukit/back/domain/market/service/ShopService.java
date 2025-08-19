package com.mukit.back.domain.market.service;

import com.mukit.back.domain.market.entity.Market;
import com.mukit.back.domain.market.repository.MarketRepository;
import com.mukit.back.domain.market.converter.ShopConverter;
import com.mukit.back.domain.market.dto.request.ShopRequestDTO;
import com.mukit.back.domain.market.dto.response.ShopResponseDTO;
import com.mukit.back.domain.market.entity.Shop;
import com.mukit.back.global.apiPayload.code.ShopErrorCode;
import com.mukit.back.global.apiPayload.exception.ShopException;
import com.mukit.back.domain.market.repository.ShopRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ShopService {

    private final ShopRepository shopRepository;
    private final MarketRepository marketRepository;

    public ShopResponseDTO.ShopDetail getShopDetail(Long shopId) {
        Shop shop = shopRepository.findById(shopId)
                .orElseThrow(() -> new ShopException(ShopErrorCode.SHOP_NOT_FOUND));

        return ShopConverter.toShopDetailResponseDTO(shop);
    }

    public ShopResponseDTO.ShopDetail getShopDetailByName(String shopName) {
        Shop shop = shopRepository.findByName(shopName)
                .orElseThrow(() -> new ShopException(ShopErrorCode.SHOP_NOT_FOUND));

        return ShopConverter.toShopDetailResponseDTO(shop);
    }

    public List<ShopResponseDTO.ShopDetail> getShopList() {
        return shopRepository.findAll()
                .stream().map(ShopConverter::toShopDetailResponseDTO)
                .toList();
    }

    @Transactional
    public void deleteShop(Long shopId) {
        Shop shop = shopRepository.findById(shopId)
                .orElseThrow(() -> new ShopException(ShopErrorCode.SHOP_NOT_FOUND));

        shopRepository.delete(shop);
    }

    @Transactional
    public ShopResponseDTO.CreateShop createShop(ShopRequestDTO.CreateShop shopRequestDTO) {
        Market market = marketRepository.findById(shopRequestDTO.marketId())
                .orElseThrow(() -> new ShopException(ShopErrorCode.MARKET_NOT_FOUND));

        Shop shop = ShopConverter.toShop(shopRequestDTO, market);
        shopRepository.save(shop);

        return ShopConverter.toCreateShopResponse(shop);
    }

    @Transactional
    public void updateShop(Long shopId, ShopRequestDTO.UpdateShop dto) {
        Shop shop = shopRepository.findById(shopId)
                .orElseThrow(() -> new ShopException(ShopErrorCode.SHOP_NOT_FOUND));

        shop.update(dto.name(), dto.description(), dto.holidays(),
                dto.openTime(), dto.breakStart(), dto.breakEnd(), dto.closeTime(),
                dto.humanLevel(), dto.xPos(), dto.yPos(),
                dto.category(), dto.imageUrl(), dto.location(), dto.phone(), dto.note());
    }

}
