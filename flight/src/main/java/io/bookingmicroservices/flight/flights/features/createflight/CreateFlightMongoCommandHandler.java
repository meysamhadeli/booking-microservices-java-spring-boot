package io.bookingmicroservices.flight.flights.features.createflight;

import io.bookingmicroservices.flight.data.mongo.entities.FlightDocument;
import io.bookingmicroservices.flight.data.mongo.repositories.FlightReadRepository;
import io.bookingmicroservices.flight.flights.exceptions.FlightAlreadyExistException;
import io.bookingmicroservices.flight.flights.features.Mappings;
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
