package org.vallegrande.repository;

import javax.enterprise.context.ApplicationScoped;
import org.bson.types.ObjectId;
import io.quarkus.mongodb.panache.reactive.ReactivePanacheMongoRepository;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import org.vallegrande.entity.Status;
import org.vallegrande.entity.Institute;


@ApplicationScoped
public class InstituteRepository implements ReactivePanacheMongoRepository<Institute>{
    public Uni<Institute> findByName(String name) {
        return find("name", name).firstResult();
    }

    public Multi<Institute> findActive() {
        return stream("status", Status.ACTIVATED);
    }

    public static Uni<Institute> updateInstitute(String id, Institute object){
        Uni<Institute> instituteUni = Institute.findById(new ObjectId(id));
        return instituteUni
                .onItem().transform(institute -> {
                    institute.setName(object.getName());
                    return institute;
                }).call(institute -> institute.persistOrUpdate());
    }

    public Multi<Institute> streamAllInstitute() {
        return streamAll();
    }
}
