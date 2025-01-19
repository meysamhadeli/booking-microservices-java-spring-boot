package io.bookingmicroservices.flight.airports.valueobjects;

import buildingblocks.utils.validation.ValidationUtils;

public record Address(String value) {
  public Address {
    ValidationUtils.notBeNullOrEmpty(value);
  }
}

