package com.example.kafka.producer.expose;

import com.example.kafka.producer.model.Product;
import com.example.kafka.producer.producer.KafkaSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;

@RestController
@RequestMapping("/v1/kafka/sender")
public class ProducerController {

    @Autowired
    KafkaSender kafkaSender;

    @PostMapping()
    public HttpStatus sendKafka(@RequestBody Product product) {
        kafkaSender.sendCustomMessage(product, "topic-demo");
        return HttpStatus.OK;
    }

}
