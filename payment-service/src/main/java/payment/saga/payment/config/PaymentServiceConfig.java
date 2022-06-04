package payment.saga.payment.config;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import payment.saga.payment.handler.EventHandler;
import payment.saga.payment.model.events.OrderPurchaseEvent;
import payment.saga.payment.model.events.PaymentEvent;
import payment.saga.payment.model.events.TransactionEvent;

import java.util.function.Function;

@Configuration
@AllArgsConstructor
public class PaymentServiceConfig {

    @Autowired
    private final EventHandler<PaymentEvent, TransactionEvent> paymentEventHandler;

    @Autowired
    private final EventHandler<OrderPurchaseEvent, PaymentEvent> orderPurchaseEventHandler;


    @Bean
    public Function<OrderPurchaseEvent, PaymentEvent> orderPurchaseEventProcessor() {
        return orderPurchaseEventHandler::handleEvent;
    }

    @Bean
    public Function<PaymentEvent, TransactionEvent> paymentEventSubscriber() {
        return paymentEventHandler::handleEvent;
    }

}
