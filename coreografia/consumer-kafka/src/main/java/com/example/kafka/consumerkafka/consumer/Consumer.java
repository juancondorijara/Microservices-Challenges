package com.example.kafka.consumerkafka.consumer;

import com.example.kafka.consumerkafka.model.Product;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class Consumer {

    @KafkaListener(topics = "topic-demo", containerFactory = "kafkaListenerContainerFactory")
    public void listen(@Payload Product msg, @Headers MessageHeaders headers) {
        log.info("Mensaje recibido: " + msg);
    }

}
