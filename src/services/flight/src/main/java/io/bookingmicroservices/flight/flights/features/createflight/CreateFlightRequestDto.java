package io.bookingmicroservices.flight.flights.features.createflight;

import io.bookingmicroservices.flight.flights.enums.FlightStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record CreateFlightRequestDto(
  String flightNumber,
  UUID aircraftId,
  UUID departureAirportId,
  LocalDateTime departureDate,
  LocalDateTime arriveDate,
  UUID arriveAirportId,
  BigDecimal durationMinutes,
  LocalDateTime flightDate,
  FlightStatus status,
  BigDecimal price){
}
