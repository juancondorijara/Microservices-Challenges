package com.ms.vallegrande.order.domain;

import lombok.Data;
import lombok.Builder;

import java.math.BigDecimal;

@Data
@Builder
public class Product {
    private String id;
    private String name;
    private Integer stock;
    private BigDecimal price;
}
