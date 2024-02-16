package com.example.payment_service.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ProductType{

    NORMAL("일반 상품"),
    PRE_ORDER("예약 상품");


    private final String text;
}
