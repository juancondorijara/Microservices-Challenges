package com.example.certidigital.infraestructure;

import com.example.certidigital.domain.Payment;
import com.example.certidigital.domain.PaymentRepository;

public class PaymentDB implements PaymentRepository {
   //db connection
   public Payment save(Payment payment){
      Payment paymentResult = new Payment();
      paymentResult.setId("1");
      paymentResult.setTipo(payment.getTipo());
      paymentResult.setNumero(payment.getNumero());
      paymentResult.setVencimiento(payment.getVencimiento());
      paymentResult.setCvv(payment.getCvv());
      return paymentResult;
   }
}
