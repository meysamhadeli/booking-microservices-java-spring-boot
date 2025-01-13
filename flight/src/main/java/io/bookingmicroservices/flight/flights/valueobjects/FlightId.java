package io.bookingmicroservices.flight.flights.valueobjects;

import buildingblocks.utils.validation.ValidationUtils;

import java.util.UUID;

public record FlightId(UUID value) {
    public FlightId {
      ValidationUtils.notBeNullOrEmpty(value);
    }
}
