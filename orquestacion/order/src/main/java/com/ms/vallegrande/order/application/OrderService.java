package com.ms.vallegrande.order.application;

import com.ms.vallegrande.order.domain.Order;
import com.ms.vallegrande.order.domain.OrderRepository;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public Order saveOrder(Order order){
        return this.orderRepository.save(order);
    }

    public void updateOrder(Order order){
        this.orderRepository.update(order);
    }
}
