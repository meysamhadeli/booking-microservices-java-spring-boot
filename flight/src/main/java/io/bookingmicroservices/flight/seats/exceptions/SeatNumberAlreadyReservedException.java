package io.bookingmicroservices.flight.seats.exceptions;

import buildingblocks.core.exception.BadRequestException;

public class SeatNumberAlreadyReservedException extends BadRequestException {
  public SeatNumberAlreadyReservedException() {
    super("Seat number already reserved!");
  }
}

