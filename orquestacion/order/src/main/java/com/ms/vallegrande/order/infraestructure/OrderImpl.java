package com.ms.vallegrande.order.infraestructure;

import com.ms.vallegrande.order.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.stereotype.Repository;

@Repository
public class OrderImpl implements OrderRepository {

    @Autowired
    MongoOperations mongoOperations;

    @Autowired
    ProductClient productClient;

    public OrderImpl(MongoOperations mongoOperations) {
        this.mongoOperations = mongoOperations;
    }

    @Override
    public Order save(Order order) {
        Product product = productClient.findItemById(order.getProduct().getId());
        if(product != null){
            order.setProduct(product);
            return this.mongoOperations.save(order);
        }
        return null;
    }

    @Override
    public void update(Order order) {
        this.mongoOperations.save(order);
    }

}
