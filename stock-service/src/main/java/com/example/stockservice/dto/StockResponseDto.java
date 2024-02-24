package com.example.stockservice.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class StockResponseDto {

    private String productId;
    private int stock;
}
