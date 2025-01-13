package io.bookingmicroservices.flight.flights.events;

import buildingblocks.core.event.DomainEvent;
import io.bookingmicroservices.flight.flights.enums.FlightStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record FlightUpdatedDomainEvent(
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
