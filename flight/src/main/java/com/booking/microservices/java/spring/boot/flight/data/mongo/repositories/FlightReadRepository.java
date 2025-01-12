package com.booking.microservices.java.spring.boot.flight.data.mongo.repositories;

import com.booking.microservices.java.spring.boot.flight.data.mongo.entities.FlightDocument;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.UUID;

public interface FlightReadRepository extends MongoRepository<FlightDocument, ObjectId> {
  FlightDocument findByFlightIdAndIsDeletedFalse(UUID flightId);
}
