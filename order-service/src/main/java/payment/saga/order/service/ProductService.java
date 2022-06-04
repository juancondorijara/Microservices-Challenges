package payment.saga.order.service;

import payment.saga.order.model.Product;
import reactor.core.publisher.Flux;

public interface ProductService {
    Flux<Product> getAll();
}
