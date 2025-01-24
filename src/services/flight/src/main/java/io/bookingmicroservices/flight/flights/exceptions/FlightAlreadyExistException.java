package io.bookingmicroservices.flight.flights.exceptions;

import buildingblocks.core.exception.ConflictException;

public class FlightAlreadyExistException extends ConflictException {
    public FlightAlreadyExistException() {
        super("Flight already exists!");
    }
}
