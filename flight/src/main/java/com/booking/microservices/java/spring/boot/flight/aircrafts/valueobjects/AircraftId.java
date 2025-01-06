package com.booking.microservices.java.spring.boot.flight.aircrafts.valueobjects;

import buildingblocks.utils.validation.ValueObjectValidator;

import java.util.UUID;

public record AircraftId(UUID value) {
    public AircraftId {
      ValueObjectValidator.assertNonNullOrEmpty(value);
    }

    public static AircraftId of(UUID value) {
        return new AircraftId(value);
    }
}
