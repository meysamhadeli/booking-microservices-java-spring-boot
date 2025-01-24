package io.bookingmicroservices.flight.seats.exceptions;

import buildingblocks.core.exception.ConflictException;

public class SeatAlreadyExistException extends ConflictException {
  public SeatAlreadyExistException() {
    super("Seat already exists!");
  }
}

