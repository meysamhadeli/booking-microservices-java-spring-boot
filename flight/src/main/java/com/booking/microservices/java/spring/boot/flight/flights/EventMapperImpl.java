package com.booking.microservices.java.spring.boot.flight.flights;

import buildingblocks.contracts.FlightContracts;
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
        case FlightCreatedDomainEvent e -> new FlightContracts.FlightCreated(e.id());
        case FlightUpdatedDomainEvent e -> new FlightContracts.FlightUpdated(e.id());
        case FlightDeletedDomainEvent e -> new FlightContracts.FlightDeleted(e.id());
        default -> null;
      };
  }
}
