package com.booking.microservices.java.spring.boot.flight.flights.exceptions;

import buildingblocks.core.exception.NotFoundException;

public class FlightNotFoundException extends NotFoundException {
    public FlightNotFoundException() {
        super("Flight not found!");
    }
}
