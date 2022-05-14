package com.example.certidigital.rest;

import com.example.certidigital.application.PaymentService;
import com.example.certidigital.domain.Payment;
import com.example.certidigital.domain.PaymentRepository;
import com.example.certidigital.infraestructure.PaymentDB;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/payment")
public class PaymentController {

   //
   PaymentRepository paymentRepository = new PaymentDB();

   PaymentService paymentService = new PaymentService(paymentRepository);

   @PostMapping
   public Payment savePayment(@RequestBody Payment payment){
     //Payment payment = new Payment();
     return paymentService.savePayment(payment);
   }

}
