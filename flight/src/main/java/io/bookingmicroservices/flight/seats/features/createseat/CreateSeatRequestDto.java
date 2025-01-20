package io.bookingmicroservices.flight.seats.features.createseat;

import io.bookingmicroservices.flight.seats.enums.SeatClass;
import io.bookingmicroservices.flight.seats.enums.SeatType;
import java.util.UUID;

public record CreateSeatRequestDto(
  String seatNumber,
  SeatType seatType,
  SeatClass seatClass,
  UUID flightId){
}

