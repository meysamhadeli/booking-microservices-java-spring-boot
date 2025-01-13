package io.bookingmicroservices.flight.flights.valueobjects;

import buildingblocks.utils.validation.ValidationUtils;

import java.math.BigDecimal;

public record DurationMinutes(BigDecimal value) {
    public DurationMinutes {
      ValidationUtils.notBeNegativeOrNull(value);
    }
}
