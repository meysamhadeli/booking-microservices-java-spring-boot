package com.booking.microservices.java.spring.boot.flight.flights.valueobjects;

import buildingblocks.utils.validation.ValueObjectValidator;
import java.time.LocalDateTime;

public record DepartureDate(LocalDateTime value) {
    public DepartureDate {
        ValueObjectValidator.assertNonNullOrEmpty(value);
    }
}
