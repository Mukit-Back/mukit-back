package com.mukit.back.domain.market.converter;

import com.mukit.back.domain.market.dto.request.MenuRequestDTO;
import com.mukit.back.domain.market.dto.response.MenuResponseDTO;
import com.mukit.back.domain.market.entity.Menu;
import com.mukit.back.domain.market.entity.Shop;

import java.util.ArrayList;
import java.util.List;

public class MenuConverter {

    public static Menu toMenu(MenuRequestDTO.CreateMenu createMenu, Shop shop, String menuImageUrl) {
        return Menu.builder()
                .name(createMenu.name())
                .description(createMenu.description())
                .fullLevel(createMenu.fullLevel())
                .price(createMenu.price())
                .spicyLevel(createMenu.spicyLevel())
                .shop(shop)
                .menuImageUrl(menuImageUrl)
                .build();
    }

    public static MenuResponseDTO.CreateMenu toCreateMenuResponse(Menu menu) {
        return MenuResponseDTO.CreateMenu.builder()
                .menuId(menu.getMenuId())
                .build();
    }

    public static MenuResponseDTO.UpdateMenu toUpdateMenuResponse(Menu menu) {
        return MenuResponseDTO.UpdateMenu.builder()
                .name(menu.getName())
                .description(menu.getDescription())
                .fullLevel(menu.getFullLevel())
                .price(menu.getPrice())
                .spicyLevel(menu.getSpicyLevel())
                .menuId(menu.getMenuId())
                .build();
    }

    public static MenuResponseDTO.MenuList toMenuList(List<Menu> menus) {
        List<MenuResponseDTO.MenuPreview> menuLists = menus.stream().map(MenuConverter::toMenuPreview).toList();

        return MenuResponseDTO.MenuList.builder()
                .menuPreviewList(menuLists)
                .build();
    }

    public static MenuResponseDTO.MenuPreview toMenuPreview(Menu menu) {
        return MenuResponseDTO.MenuPreview.builder()
                .menuId(menu.getMenuId())
                .name(menu.getName())
                .description(menu.getDescription())
                .price(menu.getPrice())
                .ImageUrl(menu.getMenuImageUrl())
                .build();
    }

}
