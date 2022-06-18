package payment.saga.payment.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import payment.saga.payment.model.User;
import payment.saga.payment.repository.UserRepository;
import payment.saga.payment.service.UserService;
import reactor.core.publisher.Flux;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    @Autowired
    private final UserRepository userRepository;

    @Override
    public Flux<User> getAll() {
        return Flux.fromIterable(userRepository.findAll());
    }
}
