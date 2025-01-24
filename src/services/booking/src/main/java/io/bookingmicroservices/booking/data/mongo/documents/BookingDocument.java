package io.bookingmicroservices.booking.data.mongo.documents;

import io.bookingmicroservices.booking.bookings.valueobjects.PassengerInfo;
import io.bookingmicroservices.booking.bookings.valueobjects.Trip;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

@Document(collection = "bookings")
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class BookingDocument {
    @Id
    private ObjectId id;
    private UUID bookingId;
    private PassengerInfo passengerInfo;
    private Trip trip;
    private boolean isDeleted;

    public BookingDocument(UUID bookingId, PassengerInfo passengerInfo, Trip trip, boolean isDeleted) {
      this.bookingId = bookingId;
      this.passengerInfo = passengerInfo;
      this.trip = trip;
      this.isDeleted = isDeleted;
    }
}
