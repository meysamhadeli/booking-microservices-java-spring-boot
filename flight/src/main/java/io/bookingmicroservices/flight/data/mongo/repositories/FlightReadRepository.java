package io.bookingmicroservices.flight.data.mongo.repositories;

import io.bookingmicroservices.flight.data.mongo.entities.FlightDocument;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.UUID;

public interface FlightReadRepository extends MongoRepository<FlightDocument, ObjectId> {
  FlightDocument findByFlightIdAndIsDeletedFalse(UUID flightId);
}
