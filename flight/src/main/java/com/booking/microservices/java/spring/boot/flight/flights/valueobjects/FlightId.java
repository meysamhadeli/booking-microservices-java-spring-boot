package com.booking.microservices.java.spring.boot.flight.flights.valueobjects;

import buildingblocks.utils.validation.ValueObjectValidator;
import java.util.UUID;

public record FlightId(UUID value) {
    public FlightId {
        ValueObjectValidator.assertNonNullOrEmpty(value);
    }
}