package com.example.product_service.service;


import com.example.product_service.domain.ProductType;
import com.example.product_service.domain.dto.ProductDto;
import com.example.product_service.domain.entity.Product;
import com.example.product_service.repository.ProductRepository;
import com.example.product_service.repository.ProductRedisRepository;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class ProductService {

    private final ProductRepository productRepository;

    private final ProductRedisRepository productRedisRepository;

    //   private final StockRepository stockRepository;


    public List<ProductDto> getProductList() {
        List<Product> products = productRepository.findAll();

        return products.stream().map(product -> ProductDto.builder()
                .productId(product.getProductNumber())
                .name(product.getName())
                .productType(product.getProductType().toString())
                .price(product.getPrice())
                .build()
        ).collect(Collectors.toList());
    }

    public List<ProductDto> getPreOrderProducts() {

        List<Product> products = productRepository.findALlByProductType(ProductType.PRE_ORDER);

        return products.stream().map(product -> ProductDto.builder()
                .productId(product.getProductNumber())
                .name(product.getName())
                .productType(product.getProductType().toString())
                .price(product.getPrice())
                .build()
        ).collect(Collectors.toList());
    }


    public List<ProductDto> getOrdinaryProducts() {

        List<Product> products = productRepository.findALlByProductType(ProductType.NORMAL);

        return products.stream().map(product -> ProductDto.builder()
                .productId(product.getProductNumber())
                .name(product.getName())
                .productType(product.getProductType().toString())
                .price(product.getPrice())
                .build()
        ).collect(Collectors.toList());
    }

    public ProductDto getProductInfo(String productId) {

        Map<String, Object> product = productRedisRepository.getProduct(productId);
        log.info("product={}", product);
        if (product.isEmpty()) {
            log.info("redis에 없으므로 db에서 조회");
            Product productInDB = productRepository.findByProductNumber(productId)
                    .orElseThrow(() -> new IllegalArgumentException("잘못된 상품 번호 입니다."));
            saveCache(productId, productInDB);

            product = productRedisRepository.getProduct(productId);

        }

        return parsing(productId, product);

    }


    private void saveCache(String productId, Product product) {
        HashMap<String, String> map = new HashMap<>();
        map.put("name", product.getName());
        map.put("type", product.getProductType().toString());
        map.put("price", String.valueOf(product.getPrice()));
        productRedisRepository.saveProduct(productId, map, 1);

    }

    private ProductDto parsing(String productId, Map productMap) {
        return ProductDto.builder()
                .productId(productId)
                .name((String) productMap.get("name"))
                .price(Integer.parseInt((String) productMap.get("price")))
                .productType((String) productMap.get("type"))
                .build();

    }


}
