package com.booking.microservices.java.spring.boot.flight.airports.valueobjects;

import buildingblocks.utils.validation.ValueObjectValidator;

import java.util.UUID;

public record AirportId(UUID value) {
    public AirportId {
      ValueObjectValidator.assertNonNullOrEmpty(value);
    }
}
