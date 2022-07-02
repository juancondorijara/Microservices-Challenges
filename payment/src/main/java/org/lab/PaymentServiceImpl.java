package org.lab;

import java.util.UUID;

import javax.enterprise.context.RequestScoped;
import javax.transaction.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequestScoped
@RequiredArgsConstructor
@Slf4j
public class PaymentServiceImpl implements IPaymentService {
    
    private final PaymentRepository repository;

    @Transactional
    @Override
    public void createPayment(Payment payment) {
        log.info("Create payment with id {}", payment.getPaymentId());
        repository.persist(payment);
    }

    @Override
    public Payment getPaymentById(UUID id) {
        return repository.find("paymentId", id).firstResult();
    }
    
}
