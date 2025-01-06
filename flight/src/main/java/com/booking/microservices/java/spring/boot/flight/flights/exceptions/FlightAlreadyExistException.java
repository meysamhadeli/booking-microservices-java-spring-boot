package com.booking.microservices.java.spring.boot.flight.flights.exceptions;

import buildingblocks.core.exception.ConflictException;

public class FlightAlreadyExistException extends ConflictException {
    public FlightAlreadyExistException() {
        super("Flight already exists!");
    }
}
