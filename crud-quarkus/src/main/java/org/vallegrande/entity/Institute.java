package org.vallegrande.entity;

import org.bson.types.ObjectId;
import io.quarkus.mongodb.panache.common.MongoEntity;
import io.quarkus.mongodb.panache.reactive.ReactivePanacheMongoEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@MongoEntity(collection = "institute")
public class Institute extends ReactivePanacheMongoEntity  {
    private ObjectId id;
    private String ruc;
    private String name;
    private String address;
    private String email;
}
