package org.lab.processor;
import io.smallrye.mutiny.Uni;
import javax.enterprise.context.ApplicationScoped;

import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.lab.pojo.Payment;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.smallrye.reactive.messaging.kafka.Record;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import javax.inject.Inject;
import io.quarkus.mailer.reactive.ReactiveMailer;
import io.quarkus.mailer.Mail;
import io.quarkus.mailer.Mailer;
import io.smallrye.common.annotation.Blocking;


@ApplicationScoped
@Slf4j
public class NotificationProcessor {

    @Inject
    ReactiveMailer reactiveMailer;


    @Incoming("payment-requests")
    @SneakyThrows
    public Uni<Void> process(Record<String, String> record)  {

        ObjectMapper objectMapper = new ObjectMapper();
        var payment = objectMapper.readValue(record.value(), Payment.class);

        try {
            
        log.info("Topics paymentid {} | mount {}", record.key(), payment.getMount());
        log.info("Aqui llegue");
        
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    
    return reactiveMailer.send(                         // <4>
                Mail.withText("govasandovalrosales@gmail.com",
                        "Pago Realizado COD "+record.key(),
                        "La orden de pago es "+record.key()+" con el monto de "+payment.getMount()
                )
        );
}
}
