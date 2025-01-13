package io.bookingmicroservices.flight.flights.features.createflight;

import buildingblocks.core.event.InternalCommand;
import buildingblocks.mediator.abstractions.commands.ICommand;
import io.bookingmicroservices.flight.flights.dtos.FlightDto;
import io.bookingmicroservices.flight.flights.enums.FlightStatus;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public record CreateFlightCommand(
  UUID id,
  String flightNumber,
  UUID aircraftId,
  UUID departureAirportId,
  LocalDateTime departureDate,
  LocalDateTime arriveDate,
  UUID arriveAirportId,
  BigDecimal durationMinutes,
  LocalDateTime flightDate,
  FlightStatus status,
  BigDecimal price) implements ICommand<CompletableFuture<FlightDto>>, InternalCommand {
}
