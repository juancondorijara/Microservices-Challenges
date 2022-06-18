package payment.saga.order.service;

import payment.saga.order.model.Order;
import payment.saga.order.model.OrderPurchase;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public interface OrderService {
    Mono<OrderPurchase> createOrder(Order order);

    Flux<OrderPurchase> getAll();

    Flux<List<OrderPurchase>> reactiveGetAll();

    Mono<OrderPurchase> getOrderById(Integer id);
}
