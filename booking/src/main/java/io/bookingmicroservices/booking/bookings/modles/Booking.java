package io.bookingmicroservices.booking.bookings.modles;

import buildingblocks.core.model.AggregateRoot;
import io.bookingmicroservices.booking.bookings.features.createbooking.BookingCreatedDomainEvent;
import io.bookingmicroservices.booking.bookings.valueobjects.BookingId;
import io.bookingmicroservices.booking.bookings.valueobjects.PassengerInfo;
import io.bookingmicroservices.booking.bookings.valueobjects.Trip;
import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Getter
@Setter(AccessLevel.PRIVATE)
public class Booking extends AggregateRoot<BookingId> {

    PassengerInfo passengerInfo;
    Trip trip;

    public Booking(BookingId bookingId, PassengerInfo passengerInfo, Trip trip, LocalDateTime createdAt, Long createdBy, LocalDateTime lastModified, Long lastModifiedBy, Long version, boolean isDeleted) {
        this.id = bookingId;
        this.passengerInfo = passengerInfo;
        this.trip = trip;
        this.createdAt = createdAt;
        this.createdBy = createdBy;
        this.lastModified = lastModified;
        this.lastModifiedBy = lastModifiedBy;
        this.version = version;
        this.isDeleted = isDeleted;
    }

    public Booking(BookingId bookingId, PassengerInfo passengerInfo, Trip trip) {
        this.id = bookingId;
        this.passengerInfo = passengerInfo;
        this.trip = trip;
    }

    public static Booking create(BookingId bookingId, PassengerInfo passengerInfo, Trip trip) {
        var booking = new Booking(bookingId, passengerInfo, trip);

        booking.addDomainEvent(new BookingCreatedDomainEvent(
                booking.getId().getBookingId(),
                booking.passengerInfo,
                booking.trip,
                false
        ));

        return booking;
    }
}
