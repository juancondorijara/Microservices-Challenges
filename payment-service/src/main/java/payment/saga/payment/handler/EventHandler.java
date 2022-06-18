package payment.saga.payment.handler;

import payment.saga.payment.model.events.Event;

public interface EventHandler<T extends Event, R extends Event> {

    R handleEvent(T event);
}
