package com.mukit.back.global.apiPayload.exception;

import com.mukit.back.global.apiPayload.code.BaseErrorCode;

public class MenuException extends CustomException {
    public MenuException(BaseErrorCode code) {
        super(code);
    }
}
