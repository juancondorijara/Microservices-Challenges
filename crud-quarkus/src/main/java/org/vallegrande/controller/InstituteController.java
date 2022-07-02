package org.vallegrande.controller;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.jboss.logging.Logger;
import org.bson.types.ObjectId;

import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import org.vallegrande.entity.Institute;
import org.vallegrande.repository.InstituteRepository;

@Path("/institute")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class InstituteController {
    @Inject
    InstituteRepository repository;

    private static final Logger LOGGER = Logger.getLogger(org.vallegrande.controller.InstituteController.class);

    @GET
    public Multi<Institute> list() {
        LOGGER.infof("InstituteResource invocation returning successfully");
        return repository.streamAllInstitute();
    }

    @GET
    @Path("/{id}")
    public Uni<Institute> get(@PathParam("id") String id) {
        return repository.findById(new ObjectId(id));
    }

    @POST
    public Uni<Response> create(Institute institute) {
        return repository.persist(institute).map(respose -> Response.status(201).build());
    }

    @PUT
    @Path("/{id}")
    public Uni<Response> update(@PathParam("id") String id, Institute institute) {
        institute.setId(new ObjectId(id));
        return institute.update().map(r -> Response.accepted().build());
    }

    @DELETE
    @Path("/{id}")
    public Uni<Response> delete(@PathParam("id") String id) {
        return repository.deleteById(new ObjectId(id)).map(response -> Response.status(204).build());
    }

    @DELETE
    public Uni<Response> deleteAll() {
        return repository.deleteAll().map(response -> Response.status(204).build());
    }

    @GET
    @Path("/search/{name}")
    public Uni<Response> search(@PathParam("name") String name) {
        return repository.findByName(name).map(response -> Response.status(200).build());
    }

}
