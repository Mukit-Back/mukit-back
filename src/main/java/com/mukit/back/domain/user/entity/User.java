package com.mukit.back.domain.user.entity;

import com.mukit.back.domain.market.entity.Course;
import com.mukit.back.domain.market.entity.enums.FullLevel;
import com.mukit.back.domain.market.entity.enums.HumanLevel;
import com.mukit.back.domain.market.entity.enums.MarketType;
import com.mukit.back.domain.market.entity.enums.SpicyLevel;
import com.mukit.back.global.apiPayload.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

import static lombok.AccessLevel.PRIVATE;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Table(name = "user")
@Getter
@Builder
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor(access = PRIVATE)
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Enumerated(EnumType.STRING)
    private MarketType market;

    @Enumerated(EnumType.STRING)
    private HumanLevel humanLevel;

    @Enumerated(EnumType.STRING)
    private SpicyLevel spicyLevel;

    @Enumerated(EnumType.STRING)
    private FullLevel fullLevel;

    @OneToMany(mappedBy = "user")
    private List<Course> courses = new ArrayList<>();
}
