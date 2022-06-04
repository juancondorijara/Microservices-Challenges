package payment.saga.payment.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import payment.saga.payment.model.Transaction;
import payment.saga.payment.service.TransactionService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("/transactions")
@AllArgsConstructor
@Tag(name = "Transactions", description = "Manage all transactions")
public class TransactionController {

    @Autowired
    private final TransactionService transactionService;

    @GetMapping
    @Operation(summary = "Obtain all transactions", description = "Obtain all transactions of orders")
    public Flux<Transaction> getAllOrders() {
        return transactionService.getAll();
    }

    @GetMapping(path = "/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    @Operation(summary = "Obtain all transactions in stream protocol", description = "Obtain all transactions in stream protocol for asynchronous communication of orders")
    public Flux<List<Transaction>> getAllOrdersStream() {
        return transactionService.reactiveGetAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtain a specific transaction", description = "Obtain a specific transaction by identifier of orders")
    public Mono<Transaction> getOrderById(@PathVariable Integer id) {
        return transactionService.getOrderById(id);
    }

}
