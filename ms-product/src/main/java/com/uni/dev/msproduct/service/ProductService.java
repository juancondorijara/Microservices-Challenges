package com.uni.dev.msproduct.service;

import com.uni.dev.msproduct.model.Product;

import java.util.List;

public interface ProductService {

    List<Product> findAll();

    Product create(Product product);

    Product update(Product product);

    void delete(String id);

}
