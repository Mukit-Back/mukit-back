package com.mukit.back.domain.user.dto;

import com.mukit.back.domain.user.entity.FullLevel;
import com.mukit.back.domain.user.entity.HumanLevel;
import com.mukit.back.domain.user.entity.MarketType;
import com.mukit.back.domain.user.entity.SpicyLevel;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class UserRequestDto {
    private MarketType marketType;
    private HumanLevel humanLevel;
    private SpicyLevel spicyLevel;
    private FullLevel fullLevel;
}
