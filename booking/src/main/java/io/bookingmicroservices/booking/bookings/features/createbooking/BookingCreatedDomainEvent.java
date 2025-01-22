package io.bookingmicroservices.booking.bookings.features.createbooking;

import buildingblocks.core.event.DomainEvent;
import io.bookingmicroservices.booking.bookings.valueobjects.PassengerInfo;
import io.bookingmicroservices.booking.bookings.valueobjects.Trip;
import java.util.UUID;


public record BookingCreatedDomainEvent(
        UUID id,
        PassengerInfo passengerInfo,
        Trip trip,
        boolean isDeleted) implements DomainEvent {
}
