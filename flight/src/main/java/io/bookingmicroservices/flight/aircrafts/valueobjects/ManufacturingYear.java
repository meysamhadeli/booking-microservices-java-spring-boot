package io.bookingmicroservices.flight.aircrafts.valueobjects;

import buildingblocks.utils.validation.ValidationUtils;

public record ManufacturingYear(int value) {
  public ManufacturingYear {
    ValidationUtils.notBeNegativeOrNull(value);
  }
}

