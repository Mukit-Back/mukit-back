package com.mukit.back.domain.market.converter;

import com.mukit.back.domain.market.entity.Market;
import com.mukit.back.domain.market.dto.request.ShopRequestDTO;
import com.mukit.back.domain.market.dto.response.ShopResponseDTO;
import com.mukit.back.domain.market.entity.Shop;

public class ShopConverter {

    public static ShopResponseDTO.ShopDetail toShopDetailResponseDTO(Shop shop) {
        return ShopResponseDTO.ShopDetail.builder()
                .shopId(shop.getShopId())
                .name(shop.getName())
                .description(shop.getDescription())
                .location(shop.getLocation())
                .holidays(shop.getHolidays())
                .openTime(shop.getOpenTime())
                .breakStart(shop.getBreakStart())
                .breakEnd(shop.getBreakEnd())
                .closeTime(shop.getCloseTime())
                .humanLevel(shop.getHumanLevel())
                .xPos(shop.getXPos())
                .yPos(shop.getYPos())
                .category(shop.getCategory())
                .imageUrl(shop.getImageUrl())
                .phone(shop.getPhone())
                .note(shop.getNote())
                .build();
    }

    public static Shop toShop(ShopRequestDTO.CreateShop dto, Market market, String imageUrl) {
        return Shop.builder()
                .market(market)
                .name(dto.name())
                .description(dto.description())
                .location(dto.location())
                .holidays(dto.holidays())
                .openTime(dto.openTime())
                .breakStart(dto.breakStart())
                .breakEnd(dto.breakEnd())
                .closeTime(dto.closeTime())
                .humanLevel(dto.humanLevel())
                .xPos(dto.xPos())
                .yPos(dto.yPos())
                .category(dto.category())
                .imageUrl(imageUrl)
                .phone(dto.phone())
                .note(dto.note())
                .build();
    }

    public static ShopResponseDTO.CreateShop toCreateShopResponse(Shop shop) {
        return ShopResponseDTO.CreateShop.builder()
                .shopId(shop.getShopId())
                .build();
    }
}
