package com.ms.vallegrande.order.rest;

import com.ms.vallegrande.order.application.OrderService;
import com.ms.vallegrande.order.domain.Order;
import com.ms.vallegrande.order.domain.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/order")
public class OrderController {

    OrderRepository orderRepository;
    @Autowired
    OrderService orderService = new OrderService(orderRepository);

    @PostMapping
    private Order saveOrder(@RequestBody Order order){
        return orderService.saveOrder(order);
    }
}
