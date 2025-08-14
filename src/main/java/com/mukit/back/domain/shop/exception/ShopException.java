package com.mukit.back.domain.shop.exception;

import com.mukit.back.global.apiPayload.code.BaseErrorCode;
import com.mukit.back.global.apiPayload.exception.CustomException;

public class ShopException extends CustomException {
    public ShopException(BaseErrorCode code) {
        super(code);
    }
}