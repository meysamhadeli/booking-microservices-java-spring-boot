package io.bookingmicroservices.flight.flights.valueobjects;

import buildingblocks.utils.validation.ValidationUtils;

public record FlightNumber(String value) {
    public FlightNumber {
      ValidationUtils.notBeNullOrEmpty(value);
    }
}
