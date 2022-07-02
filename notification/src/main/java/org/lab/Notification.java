package org.lab;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import io.quarkus.mailer.reactive.ReactiveMailer;
import io.quarkus.mailer.Mail;
import io.quarkus.mailer.Mailer;
import javax.inject.Inject;
import io.smallrye.common.annotation.Blocking;

@Path("/hello")
public class Notification {
    @Inject
    Mailer mailer;

    @Inject
    ReactiveMailer reactiveMailer;

    @GET
    @Blocking
    public void hello() {
        mailer.send(Mail.withText("govasandovalrosales@gmail.com", "Ahoy from Quarkus", "A simple email sent from a Quarkus application."));
    }
}