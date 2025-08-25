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
import com.mukit.back.global.s3.AmazonS3Manager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ShopService {

    private final ShopRepository shopRepository;
    private final MarketRepository marketRepository;
    private final AmazonS3Manager amazonS3Manager;

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
    public ShopResponseDTO.CreateShop createShop(ShopRequestDTO.CreateShop shopRequestDTO, MultipartFile shopImage) {
        Market market = marketRepository.findById(shopRequestDTO.marketId())
                .orElseThrow(() -> new ShopException(ShopErrorCode.MARKET_NOT_FOUND));


        String shopImageUrl = null;
        if (shopImage != null) {
            shopImageUrl = amazonS3Manager.uploadShopImage(shopImage);
        }

        Shop shop = ShopConverter.toShop(shopRequestDTO, market, shopImageUrl);
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
                dto.category(), null, dto.location(), dto.phone(), dto.note());
    }

}
