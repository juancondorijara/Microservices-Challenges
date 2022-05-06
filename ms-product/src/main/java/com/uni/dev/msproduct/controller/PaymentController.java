package com.uni.dev.msproduct.controller;

import com.uni.dev.msproduct.model.Payment;
import com.uni.dev.msproduct.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/ms-payment/v1/payments")
public class PaymentController {

    @Autowired
    PaymentService paymentService;

    @GetMapping
    public List<Payment> findAll(){
        return paymentService.findAll();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Payment create(@RequestBody Payment payment){
        return paymentService.create(payment);
    }

    @PutMapping
    public Payment update(@RequestBody Payment payment) { return paymentService.update(payment);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id){
        paymentService.delete(id);
    }
}
