package payment.saga.payment.service;

import payment.saga.payment.model.User;
import reactor.core.publisher.Flux;

public interface UserService {
    Flux<User> getAll();
}
