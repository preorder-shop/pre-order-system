package com.example.payment_service.domain;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum OrderState {

    INIT("결제 진입"),
    DOING("결제 중"),
    COMPLETE("결제 완료");

    private final String text;
}
