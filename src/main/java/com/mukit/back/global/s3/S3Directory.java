package com.mukit.back.global.s3;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum S3Directory {
    MENU("market/menu"),
    SHOP("market/shop");

    private final String directory;
}
