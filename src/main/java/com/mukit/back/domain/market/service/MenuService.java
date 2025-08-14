package com.mukit.back.domain.market.service;

import com.mukit.back.domain.market.converter.MenuConverter;
import com.mukit.back.domain.market.dto.request.MenuRequestDTO;
import com.mukit.back.domain.market.dto.response.MenuResponseDTO;
import com.mukit.back.domain.market.entity.Menu;
import com.mukit.back.domain.market.entity.Shop;
import com.mukit.back.domain.market.repository.MenuRepository;
import com.mukit.back.domain.market.repository.ShopRepository;
import com.mukit.back.global.apiPayload.code.ShopErrorCode;
import com.mukit.back.global.apiPayload.exception.MenuException;
import com.mukit.back.global.apiPayload.exception.ShopException;
import com.mukit.back.global.s3.AmazonS3Manager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class MenuService {

    private final MenuRepository menuRepository;
    private final ShopRepository shopRepository;
    private final AmazonS3Manager amazonS3Manager;

    public MenuResponseDTO.CreateMenu createMenu(Long shopId, MenuRequestDTO.CreateMenu createMenu, MultipartFile menuImage) {
        Shop shop = shopRepository.findById(shopId).orElseThrow(()-> new ShopException(ShopErrorCode.SHOP_NOT_FOUND));

        String menuImageUrl = amazonS3Manager.uploadMenuImage(menuImage);

        Menu menu = MenuConverter.toMenu(createMenu, shop, menuImageUrl);

        menuRepository.save(menu);

        return MenuConverter.toCreateMenuResponse(menu);
    }

    public MenuResponseDTO.UpdateMenu updateMenu(Long menuId, MenuRequestDTO.UpdateMenu updateMenu, MultipartFile menuImage) {
        Menu menu = menuRepository.findById(menuId).orElseThrow(()-> new MenuException(ShopErrorCode.SHOP_NOT_FOUND));

        String menuImageUrl = amazonS3Manager.uploadMenuImage(menuImage);

        menu.update(updateMenu, menuImageUrl);

        return MenuConverter.toUpdateMenuResponse(menu);
    }

    public void deleteMenu(Long menuId) {
        Menu menu = menuRepository.findById(menuId).orElseThrow(()-> new MenuException(ShopErrorCode.SHOP_NOT_FOUND));

        menuRepository.delete(menu);
    }

    @Transactional(readOnly = true)
    public MenuResponseDTO.MenuList getMenus(Long shopId) {
        Shop shop = shopRepository.findById(shopId).orElseThrow(() -> new ShopException(ShopErrorCode.SHOP_NOT_FOUND));

        List<Menu> menuList = menuRepository.findAllByShop(shop);

        return MenuConverter.toMenuList(menuList);
    }
}
