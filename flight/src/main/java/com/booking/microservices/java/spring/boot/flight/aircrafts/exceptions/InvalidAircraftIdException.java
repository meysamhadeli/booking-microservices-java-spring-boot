package com.booking.microservices.java.spring.boot.flight.aircrafts.exceptions;

import buildingblocks.core.exception.BadRequestException;

import java.util.UUID;

public class InvalidAircraftIdException extends BadRequestException {
    public InvalidAircraftIdException(UUID aircraftId) {
        super("Aircraft ID: '" + aircraftId.toString() + "' is invalid.");
    }
}

