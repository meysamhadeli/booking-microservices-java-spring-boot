package io.bookingmicroservices.flight.seats.valueobjects;

import buildingblocks.utils.validation.ValidationUtils;

public record SeatNumber(String value) {
  public SeatNumber {
    ValidationUtils.notBeNullOrEmpty(value);
  }
}


