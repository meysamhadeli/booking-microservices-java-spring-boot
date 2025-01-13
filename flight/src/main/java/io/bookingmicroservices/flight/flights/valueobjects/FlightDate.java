package io.bookingmicroservices.flight.flights.valueobjects;

import buildingblocks.utils.validation.ValidationUtils;

import java.time.LocalDateTime;

public record FlightDate(LocalDateTime value) {
    public FlightDate {
      ValidationUtils.notBeNullOrEmpty(value);
    }
}
