package com.booking.microservices.java.spring.boot.flight.flights.valueobjects;

import buildingblocks.utils.validation.ValueObjectValidator;
import java.time.LocalDateTime;

public record FlightDate(LocalDateTime value) {
    public FlightDate {
        ValueObjectValidator.assertNonNullOrEmpty(value);
    }
}