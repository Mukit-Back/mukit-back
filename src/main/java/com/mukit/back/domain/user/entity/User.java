package com.mukit.back.domain.user.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private MarketType marketType;
    @Column
    private HumanLevel humanLevel;
    @Column
    private SpicyLevel spicyLevel;
    @Column
    private FullLevel fullLevel;
}
