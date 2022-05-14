package com.example.certidigital.application;

import com.example.certidigital.domain.Payment;
import com.example.certidigital.domain.PaymentRepository;

public class PaymentService {

   PaymentRepository paymentRepository;

   public PaymentService(PaymentRepository paymentRepository) {
      this.paymentRepository = paymentRepository;
   }

   public Payment savePayment(Payment payment){
      return paymentRepository.save(payment);
   }

}
