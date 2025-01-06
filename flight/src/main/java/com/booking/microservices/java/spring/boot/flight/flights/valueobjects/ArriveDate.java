package com.booking.microservices.java.spring.boot.flight.flights.valueobjects;

import buildingblocks.utils.validation.ValueObjectValidator;

import java.time.LocalDateTime;

public record ArriveDate(LocalDateTime value) {
    public ArriveDate {
        ValueObjectValidator.assertNonNullOrEmpty(value);
    }
}