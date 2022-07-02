package org.acme.processor;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.quarkus.mailer.Mail;
import io.quarkus.mailer.Mailer;
import io.quarkus.mailer.reactive.ReactiveMailer;
import io.smallrye.mutiny.Uni;
import io.smallrye.reactive.messaging.kafka.Record;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.acme.pojo.Payment;
import org.eclipse.microprofile.reactive.messaging.Incoming;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;


@ApplicationScoped
@Slf4j
public class NotificationProcessor {
    @Inject
    ReactiveMailer reactiveMailer;
    @Inject
    Mailer mailer;


    @Incoming("payment-requests")
    @SneakyThrows
    //@Blocking
    public Uni<Void> process(Record<String, String> record)  {

        //ReactiveMailer reactiveMailer = new ReactiveMailer();
        ObjectMapper objectMapper = new ObjectMapper();
        var payment = objectMapper.readValue(record.value(), Payment.class);

        try {

            log.info("Topics paymentid {} | mount {}", record.key(), payment.getMount());
            //reactiveMailer.send(Mail.withText("govasandovalrosales@gmail.com","Ahoy from Quarkus Reactivo RAA", "A simple email sent from a Quarkus application using the reactive API."));
            log.info("Aqui llegue");

            //mailer.send(Mail.withText("govasandovalrosales@gmail.com", "Ahoy from Quarkus", "A simple email sent from a Quarkus application."));

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
