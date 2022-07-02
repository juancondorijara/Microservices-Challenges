package org.lab;

import java.time.LocalDateTime;
import java.util.UUID;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.smallrye.reactive.messaging.kafka.Record;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

@Path("/payment")
@Consumes(MediaType.APPLICATION_JSON)
@RequiredArgsConstructor
public class PaymentController {
    @Inject
    private PaymentServiceImpl service;

    @Channel("payment-requests") Emitter<Record<String, String>> paymentEmitter; 

    @POST
    @Produces(MediaType.TEXT_PLAIN)
    @SneakyThrows
    public Response createOrder(PaymentDto dto) {
        UUID id = UUID.randomUUID();
        Payment payment = Payment.builder()
                .paymentId(id)
                .cashierId(dto.getCashierId())
                .userId(dto.getUserId())
                .comment(dto.getComment())
                .mount(dto.getMount())
                .createdAt(LocalDateTime.now().toString())
                .build();
        service.createPayment(payment);

        ObjectMapper objectMapper = new ObjectMapper();
        var paymentJson = objectMapper.writeValueAsString(payment);

        paymentEmitter.send(Record.of(id.toString(), paymentJson));


        return Response.status(Response.Status.CREATED)
                .entity(String.format("Payment Created with id: %s", id)).build();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getOrderById(@PathParam("id") UUID id) {
        return Response.status(Response.Status.OK)
                .entity(service.getPaymentById(id)).build();
    }

}
