package io.bookingmicroservices.flight.flights.features.deleteflight;

import buildingblocks.mediator.abstractions.commands.ICommandHandler;
import buildingblocks.mediator.abstractions.requests.Unit;
import io.bookingmicroservices.flight.data.mongo.documents.FlightDocument;
import io.bookingmicroservices.flight.data.mongo.repositories.FlightReadRepository;
import io.bookingmicroservices.flight.flights.exceptions.FlightNotFoundException;
import io.bookingmicroservices.flight.flights.features.Mappings;
import org.springframework.stereotype.Service;


@Service
public class DeleteFlightMongoCommandHandler implements ICommandHandler<DeleteFlightMongoCommand, Unit> {


  private final FlightReadRepository flightReadRepository;

  public DeleteFlightMongoCommandHandler(FlightReadRepository flightReadRepository) {
    this.flightReadRepository = flightReadRepository;
  }

  public Unit handle(DeleteFlightMongoCommand command) {

    FlightDocument flightDocument = Mappings.toFlightDocument(command);

    var flight = flightReadRepository.findByFlightIdAndIsDeletedFalse(flightDocument.getFlightId());

    if (flight == null) {
      throw new FlightNotFoundException();
    }

    flightReadRepository.save(flightDocument);

    return Unit.VALUE;
  }
}

