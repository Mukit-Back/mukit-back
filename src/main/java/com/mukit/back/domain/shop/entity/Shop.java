package com.mukit.back.domain.shop.entity;

import com.mukit.back.domain.market.entity.Course;
import com.mukit.back.domain.market.entity.Market;
import com.mukit.back.domain.market.entity.Menu;
import com.mukit.back.domain.market.entity.enums.Category;
import com.mukit.back.domain.market.entity.enums.Holiday;
import com.mukit.back.domain.market.entity.enums.HumanLevel;
import com.mukit.back.global.apiPayload.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static lombok.AccessLevel.PRIVATE;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Table(name = "shop")
@Getter
@Builder
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor(access = PRIVATE)
public class Shop extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long shopId;

    private String location;
    private String name;
    private String description;

    @Enumerated(EnumType.STRING)
    private Holiday holiday;

    private LocalTime openTime;
    private LocalTime breakStart;
    private LocalTime breakEnd;
    private LocalTime closeTime;

    @Enumerated(EnumType.STRING)
    private HumanLevel humanLevel;

    private Double xPos;
    private Double yPos;

    @Enumerated(EnumType.STRING)
    private Category category;

    private String imageUrl;

    @ManyToOne
    @JoinColumn(name = "market_id")
    private Market market;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;

    @OneToMany(mappedBy = "shop")
    private List<Menu> menus = new ArrayList<>();
}


