package com.mukit.back.domain.market.entity;

import com.mukit.back.domain.market.dto.request.MenuRequestDTO;
import com.mukit.back.domain.market.entity.enums.FullLevel;
import com.mukit.back.domain.market.entity.enums.SpicyLevel;
import com.mukit.back.global.apiPayload.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PRIVATE;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Table(name = "menu")
@Getter
@Builder
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor(access = PRIVATE)
public class Menu extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long menuId;

    private String name;
    private Integer price;
    private String description;

    @Enumerated(EnumType.STRING)
    private SpicyLevel spicyLevel;

    @Enumerated(EnumType.STRING)
    private FullLevel fullLevel;

    private String menuImageUrl;

    @ManyToOne
    @JoinColumn(name = "shop_id")
    private Shop shop;

    public void update(MenuRequestDTO.UpdateMenu updateMenu, String menuImageUrl) {
        if (updateMenu.name() != null) {
            this.name = updateMenu.name();
        }
        if (updateMenu.price() != null) {
            this.price = updateMenu.price();
        }
        if (updateMenu.description() != null) {
            this.description = updateMenu.description();
        }
        if (updateMenu.spicyLevel() != null) {
            this.spicyLevel = updateMenu.spicyLevel();
        }
        if (updateMenu.fullLevel() != null) {
            this.fullLevel = updateMenu.fullLevel();
        }
        if (menuImageUrl != null) {
            this.menuImageUrl = menuImageUrl;
        }
    }

}

