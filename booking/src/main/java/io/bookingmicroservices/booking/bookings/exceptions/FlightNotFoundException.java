package io.bookingmicroservices.booking.bookings.exceptions;

import buildingblocks.core.exception.NotFoundException;

public class FlightNotFoundException extends NotFoundException {
    public FlightNotFoundException() {
        super("Flight not found!");
    }
}
