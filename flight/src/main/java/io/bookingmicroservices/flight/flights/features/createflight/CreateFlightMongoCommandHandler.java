package io.bookingmicroservices.flight.flights.features.createflight;

import buildingblocks.mediator.abstractions.commands.ICommandHandler;
import buildingblocks.mediator.abstractions.requests.Unit;
import io.bookingmicroservices.flight.data.mongo.entities.FlightDocument;
import io.bookingmicroservices.flight.data.mongo.repositories.FlightReadRepository;
import io.bookingmicroservices.flight.flights.exceptions.FlightAlreadyExistException;
import io.bookingmicroservices.flight.flights.features.Mappings;
import org.springframework.stereotype.Service;
import java.util.concurrent.CompletableFuture;


@Service
public class CreateFlightMongoCommandHandler implements ICommandHandler<CreateFlightMongoCommand, CompletableFuture<Unit>> {
  private final FlightReadRepository flightReadRepository;

  public CreateFlightMongoCommandHandler(FlightReadRepository flightReadRepository) {
    this.flightReadRepository = flightReadRepository;
  }

  public CompletableFuture<Unit> handle(CreateFlightMongoCommand command) {

    return CompletableFuture.supplyAsync(() -> {
      FlightDocument flightDocument = Mappings.toFlightDocument(command);

      var flightExist = flightReadRepository.findByFlightIdAndIsDeletedFalse(flightDocument.getFlightId());

      if (flightExist != null) {
        throw new FlightAlreadyExistException();
      }

      flightReadRepository.save(flightDocument);

      return Unit.VALUE;
    });
  }
}
