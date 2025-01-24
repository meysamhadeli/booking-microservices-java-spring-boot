package io.bookingmicroservices.passenger.data.mongo.repositories;

import io.bookingmicroservices.passenger.data.mongo.documents.PassengerDocument;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.UUID;

public interface PassengerReadRepository extends MongoRepository<PassengerDocument, ObjectId> {
    PassengerDocument findPassengerByPassengerIdAndIsDeletedFalse(UUID passengerId);
}

