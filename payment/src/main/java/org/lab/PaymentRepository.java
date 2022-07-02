package org.lab;

import javax.enterprise.context.ApplicationScoped;

import io.quarkus.hibernate.orm.panache.PanacheRepository;

@ApplicationScoped
public class PaymentRepository implements PanacheRepository<Payment> {
    
}
