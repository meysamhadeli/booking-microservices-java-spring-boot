package io.bookingmicroservices.flight.flights.valueobjects;

import buildingblocks.utils.validation.ValidationUtils;

import java.math.BigDecimal;

public record Price(BigDecimal value) {
    public Price {
      ValidationUtils.notBeNegativeOrNull(value);
    }
}
