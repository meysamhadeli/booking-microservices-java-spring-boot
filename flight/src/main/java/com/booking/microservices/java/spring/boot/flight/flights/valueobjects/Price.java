package com.booking.microservices.java.spring.boot.flight.flights.valueobjects;

import buildingblocks.utils.validation.ValueObjectValidator;

import java.math.BigDecimal;

public record Price(BigDecimal value) {
    public Price {
        ValueObjectValidator.assertNonNegativeOrNull(value);
    }
}