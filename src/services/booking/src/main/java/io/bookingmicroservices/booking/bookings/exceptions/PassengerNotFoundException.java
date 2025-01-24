package io.bookingmicroservices.booking.bookings.exceptions;

import buildingblocks.core.exception.NotFoundException;

public class PassengerNotFoundException extends NotFoundException {
    public PassengerNotFoundException() {
        super("Passenger not found!");
    }
}
