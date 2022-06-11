package com.ms.vallegrande.order.domain;

public interface OrderRepository {

    Order save(Order order);
    void update(Order order);

}
