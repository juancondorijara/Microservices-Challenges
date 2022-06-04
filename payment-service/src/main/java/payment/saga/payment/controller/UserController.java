package payment.saga.payment.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import payment.saga.payment.model.User;
import payment.saga.payment.service.UserService;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
@Tag(name = "Users", description = "Manage users")
public class UserController {

    @Autowired
    private final UserService userService;

    @GetMapping
    @Operation(summary = "Obtain all users", description = "Obtain all users registered")
    public Flux<User> getAllUsers() {
        return userService.getAll();
    }

}
