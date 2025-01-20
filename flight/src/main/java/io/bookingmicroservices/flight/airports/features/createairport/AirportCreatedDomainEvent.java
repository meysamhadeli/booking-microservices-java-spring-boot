package io.bookingmicroservices.flight.airports.features.createairport;

import buildingblocks.core.event.DomainEvent;
import java.util.UUID;

public record AirportCreatedDomainEvent(
  UUID id,
  String name,
  String code,
  String address,
  boolean isDeleted) implements DomainEvent {
}

