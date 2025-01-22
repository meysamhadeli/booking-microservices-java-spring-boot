package io.bookingmicroservices.booking.data.mongo.repositories;

import io.bookingmicroservices.booking.data.mongo.documents.BookingDocument;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.UUID;


public interface BookingReadRepository extends MongoRepository<BookingDocument, ObjectId> {
    BookingDocument findBookingByBookingIdAndIsDeletedFalse(UUID bookingId);
}

