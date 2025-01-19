package io.bookingmicroservices.flight.airports.valueobjects;

import buildingblocks.utils.validation.ValidationUtils;

public record Code(String value) {
  public Code {
    ValidationUtils.notBeNullOrEmpty(value);
  }
}
