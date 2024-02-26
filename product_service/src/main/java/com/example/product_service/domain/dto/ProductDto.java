package com.example.product_service.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class ProductDto {

    private String productId;

    private String name;

    private String productType;

    private Integer price;
}
