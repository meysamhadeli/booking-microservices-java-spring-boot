package com.booking.microservices.java.spring.boot.flight.flights.features.create.flight;

import com.booking.microservices.java.spring.boot.flight.aircrafts.valueobjects.AircraftId;
import com.booking.microservices.java.spring.boot.flight.airports.valueobjects.AirportId;
import com.booking.microservices.java.spring.boot.flight.data.jpa.repositories.FlightRepository;
import com.booking.microservices.java.spring.boot.flight.flights.features.create.Mappings;
import com.booking.microservices.java.spring.boot.flight.flights.models.Flight;
import buildingblocks.jpa.TransactionCoordinator;
import com.booking.microservices.java.spring.boot.flight.flights.valueobjects.*;
import org.axonframework.commandhandling.CommandHandler;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@Component
public class CreateFlightCommandHandler {
  private final FlightRepository flightRepository;
  private final TransactionCoordinator transactionCoordinator;

  public CreateFlightCommandHandler(
    FlightRepository flightRepository,
    TransactionCoordinator transactionCoordinator) {
    this.flightRepository = flightRepository;
    this.transactionCoordinator = transactionCoordinator;
  }

  @CommandHandler
  @Async
  public CompletableFuture<Flight> handle(CreateFlightCommand command) {
    return CompletableFuture.supplyAsync(() ->
      transactionCoordinator.executeWithEvents(() -> {
        Flight flight = Mappings.toFlight(command);
        return flightRepository.create(flight);
      })
    );
  }
}
