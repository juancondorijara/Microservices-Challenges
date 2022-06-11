package com.ms.pedido.vallegrande.domain;

import java.util.List;

public interface ProductRepository {
    List<Product> findAll();
    Product findItemById(String id);

    Product create(Product product);

    void update(Product product);

    void delete(String id);
}
