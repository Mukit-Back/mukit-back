package com.mukit.back.domain.user.dto;

import com.mukit.back.domain.user.entity.MarketType;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class UserRequestDto {
    private MarketType marketType;
    private Integer humanLevel;
    private Integer spicyLevel;
    private Integer fullLevel;
}
