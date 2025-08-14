package com.mukit.back.global.apiPayload.exception;

import com.mukit.back.global.apiPayload.code.BaseErrorCode;

public class ShopException extends CustomException {
    public ShopException(BaseErrorCode code) {
        super(code);
    }
}
