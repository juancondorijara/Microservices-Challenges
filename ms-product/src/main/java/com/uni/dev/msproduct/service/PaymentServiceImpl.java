package com.uni.dev.msproduct.service;

import com.uni.dev.msproduct.model.Payment;
import com.uni.dev.msproduct.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    PaymentRepository paymentRepository;

    @Override
    public List<Payment> findAll() {
        return paymentRepository.findAll();
    }

    @Override
    public Payment create(Payment payment) {
        return paymentRepository.save(payment);
    }

    @Override
    public Payment update(Payment payment) {
        Optional<Payment> optionalPayment = paymentRepository.findById(payment.getId());
        if (!optionalPayment.isPresent()) throw new RuntimeException("No se encontro el pago a actualizar");
        return paymentRepository.save(payment);
    }

    @Override
    public void delete(String id) {
        Optional<Payment> optionalPayment = paymentRepository.findById(id);
        if (!optionalPayment.isPresent()) throw new RuntimeException("No se encontro el pago a eliminar");
        paymentRepository.deleteById(id);
    }

}
