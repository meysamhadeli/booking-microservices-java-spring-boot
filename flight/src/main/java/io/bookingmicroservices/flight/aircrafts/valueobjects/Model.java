package io.bookingmicroservices.flight.aircrafts.valueobjects;

import buildingblocks.utils.validation.ValidationUtils;

public record Model(String value) {
  public Model {
    ValidationUtils.notBeNullOrEmpty(value);
  }
}

