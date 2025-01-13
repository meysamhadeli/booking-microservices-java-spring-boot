package com.booking.microservices.java.spring.boot.flight.flights.features.createflight;

import com.booking.microservices.java.spring.boot.flight.data.mongo.entities.FlightDocument;
import com.booking.microservices.java.spring.boot.flight.data.mongo.repositories.FlightReadRepository;
import com.booking.microservices.java.spring.boot.flight.flights.exceptions.FlightAlreadyExistException;
import com.booking.microservices.java.spring.boot.flight.flights.features.Mappings;
import org.axonframework.commandhandling.CommandHandler;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import java.util.concurrent.CompletableFuture;

@Component
public class CreateFlightMongoCommandHandler {
  private final FlightReadRepository flightReadRepository;

  public CreateFlightMongoCommandHandler(FlightReadRepository flightReadRepository) {
    this.flightReadRepository = flightReadRepository;
  }

  @CommandHandler
  @Async
  public CompletableFuture<Void> handle(CreateFlightMongoCommand command) {

    return CompletableFuture.runAsync(() -> {
      FlightDocument flightDocument = Mappings.toFlightDocument(command);

      var flightExist = flightReadRepository.findByFlightIdAndIsDeletedFalse(flightDocument.getFlightId());

      if (flightExist != null) {
        throw new FlightAlreadyExistException();
      }

      flightReadRepository.save(flightDocument);
    });
  }
}
