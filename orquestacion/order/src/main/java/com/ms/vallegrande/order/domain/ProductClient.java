package com.ms.vallegrande.order.domain;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "product-service", fallback = ProductHystrixFallbackFactory.class)
public interface ProductClient {

    @GetMapping(value = "/product/{id}")
    public Product findItemById(@PathVariable String id);

}
