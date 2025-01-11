package com.booking.microservices.java.spring.boot.flight.flights;

import buildingblocks.contracts.flight.FlightCreated;
import buildingblocks.contracts.flight.FlightDeleted;
import buildingblocks.contracts.flight.FlightUpdated;
import buildingblocks.core.event.EventMapper;
import buildingblocks.core.event.DomainEvent;
import buildingblocks.core.event.IntegrationEvent;
import com.booking.microservices.java.spring.boot.flight.flights.events.FlightDeletedDomainEvent;
import com.booking.microservices.java.spring.boot.flight.flights.events.FlightUpdatedDomainEvent;
import com.booking.microservices.java.spring.boot.flight.flights.features.create.flight.FlightCreatedDomainEvent;
import org.springframework.stereotype.Component;

@Component
public class EventMapperImpl implements EventMapper {
  @Override
  public IntegrationEvent MapToIntegrationEvent(DomainEvent event) {
      return switch (event) {
        case FlightCreatedDomainEvent e -> new FlightCreated(e.id());
        case FlightUpdatedDomainEvent e -> new FlightUpdated(e.id());
        case FlightDeletedDomainEvent e -> new FlightDeleted(e.id());
        default -> null;
      };
  }
}
