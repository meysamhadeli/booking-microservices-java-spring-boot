package com.booking.microservices.java.spring.boot.flight.flights.features.create.flight;

import buildingblocks.core.event.DomainEvent;
import com.booking.microservices.java.spring.boot.flight.flights.enums.FlightStatus;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;


public record FlightCreatedDomainEvent(
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
  BigDecimal price,
  boolean isDeleted) implements DomainEvent {
}

