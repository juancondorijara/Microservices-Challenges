package com.ms.vallegrande.order.domain;
import org.springframework.stereotype.Component;

@Component
public class ProductHystrixFallbackFactory implements ProductClient{
    @Override
    public Product findItemById(String id) {
        Product product = Product.builder()
                .id("1")
                .name("")
                .price(null)
                .stock(0)
                .build();
        return product;
    }
}
