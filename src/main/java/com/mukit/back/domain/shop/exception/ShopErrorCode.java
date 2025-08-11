package com.mukit.back.domain.shop.exception;

import com.mukit.back.global.apiPayload.code.BaseErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum ShopErrorCode implements BaseErrorCode {

    ;

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}
