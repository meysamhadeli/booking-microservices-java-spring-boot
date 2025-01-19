package io.bookingmicroservices.flight.data.mongo.repositories;

import io.bookingmicroservices.flight.data.mongo.entities.SeatDocument;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.UUID;

public interface SeatReadRepository extends MongoRepository<SeatDocument, ObjectId> {
  SeatDocument findBySeatIdAndIsDeletedFalse(UUID seatId);
}

