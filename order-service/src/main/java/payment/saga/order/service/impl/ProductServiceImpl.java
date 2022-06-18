package payment.saga.order.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import payment.saga.order.model.Product;
import payment.saga.order.repository.ProductRepository;
import payment.saga.order.service.ProductService;
import reactor.core.publisher.Flux;

@Service
@AllArgsConstructor
public class ProductServiceImpl implements ProductService {

    @Autowired
    private final ProductRepository productRepository;

    @Override
    public Flux<Product> getAll() {
        return Flux.fromIterable(productRepository.findAll());
    }
}
