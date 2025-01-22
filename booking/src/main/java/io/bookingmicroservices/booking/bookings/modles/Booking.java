package io.bookingmicroservices.booking.bookings.modles;

import buildingblocks.core.model.AggregateRoot;
import io.bookingmicroservices.booking.bookings.features.createbooking.BookingCreatedDomainEvent;
import io.bookingmicroservices.booking.bookings.valueobjects.BookingId;
import io.bookingmicroservices.booking.bookings.valueobjects.PassengerInfo;
import io.bookingmicroservices.booking.bookings.valueobjects.Trip;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Getter
@Setter(AccessLevel.PRIVATE)
public class Booking extends AggregateRoot<BookingId> {

    PassengerInfo passengerInfo;
    Trip trip;
    boolean isDeleted;

    public Booking(BookingId bookingId, PassengerInfo passengerInfo, Trip trip, boolean isDeleted) {
        this.id = bookingId;
        this.passengerInfo = passengerInfo;
        this.trip = trip;
        this.isDeleted = isDeleted;
    }

    public static Booking create(
            BookingId bookingId,
            PassengerInfo passengerInfo,
            Trip trip,
            boolean isDeleted
    ) {
        var booking = new Booking(bookingId, passengerInfo, trip, isDeleted);

        booking.addDomainEvent(new BookingCreatedDomainEvent(
                booking.getId().getBookingId(),
                booking.passengerInfo,
                booking.trip,
                booking.isDeleted
        ));

        return booking;
    }
}
