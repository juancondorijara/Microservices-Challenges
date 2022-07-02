package org.lab;

import java.util.UUID;

public interface IPaymentService {
    void createPayment(Payment order);
    Payment getPaymentById(UUID id);
}
