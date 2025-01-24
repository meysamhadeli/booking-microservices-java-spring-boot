package io.bookingmicroservices.booking.bookings.exceptions;

import buildingblocks.core.exception.NotFoundException;


public class SeatNumberIsNotAvailableException extends NotFoundException {
    public SeatNumberIsNotAvailableException() {
        super("SeatNumber is not available!");
    }
}

