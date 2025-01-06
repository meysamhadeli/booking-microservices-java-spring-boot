package com.booking.microservices.java.spring.boot.flight.flights.valueobjects;

import buildingblocks.utils.validation.ValueObjectValidator;

public record FlightNumber(String value) {
    public FlightNumber {
        ValueObjectValidator.assertNonNullOrEmpty(value);
    }
}