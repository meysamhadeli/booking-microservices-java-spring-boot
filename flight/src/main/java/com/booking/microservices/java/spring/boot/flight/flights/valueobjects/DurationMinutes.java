package com.booking.microservices.java.spring.boot.flight.flights.valueobjects;

import buildingblocks.utils.validation.ValueObjectValidator;

import java.math.BigDecimal;

public record DurationMinutes(BigDecimal value) {
    public DurationMinutes {
        ValueObjectValidator.assertNonNegativeOrNull(value);
    }
}