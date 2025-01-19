package io.bookingmicroservices.flight.airports.valueobjects;

import buildingblocks.utils.validation.ValidationUtils;

public record Name(String value) {
  public Name {
    ValidationUtils.notBeNullOrEmpty(value);
  }
}

