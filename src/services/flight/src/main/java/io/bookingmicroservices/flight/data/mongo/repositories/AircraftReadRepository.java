package io.bookingmicroservices.flight.data.mongo.repositories;

import io.bookingmicroservices.flight.data.mongo.documents.AircraftDocument;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.UUID;

public interface AircraftReadRepository extends MongoRepository<AircraftDocument, ObjectId> {
  AircraftDocument findByAircraftIdAndIsDeletedFalse(UUID aircraftId);
}


