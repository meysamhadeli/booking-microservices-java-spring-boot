package io.bookingmicroservices.flight.flights.valueobjects;

import buildingblocks.utils.validation.ValidationUtils;

import java.time.LocalDateTime;

public record ArriveDate(LocalDateTime value) {
    public ArriveDate {
      ValidationUtils.validLocalDateTime(value);
    }
}
