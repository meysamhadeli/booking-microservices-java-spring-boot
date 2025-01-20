package io.bookingmicroservices.flight.seats.valueobjects;

import buildingblocks.utils.validation.ValidationUtils;

import java.util.UUID;

public record SeatId(UUID value) {
  public SeatId {
    ValidationUtils.notBeNullOrEmpty(value);
  }
}

