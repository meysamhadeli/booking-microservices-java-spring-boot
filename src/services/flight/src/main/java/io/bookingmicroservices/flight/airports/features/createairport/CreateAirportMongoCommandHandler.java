package io.bookingmicroservices.flight.airports.features.createairport;

import buildingblocks.mediator.abstractions.commands.ICommandHandler;
import buildingblocks.mediator.abstractions.requests.Unit;
import io.bookingmicroservices.flight.airports.exceptions.AirportAlreadyExistException;
import io.bookingmicroservices.flight.airports.features.Mappings;
import io.bookingmicroservices.flight.data.mongo.documents.AirportDocument;
import io.bookingmicroservices.flight.data.mongo.repositories.AirportReadRepository;
import org.springframework.stereotype.Service;


@Service
public class CreateAirportMongoCommandHandler implements ICommandHandler<CreateAirportMongoCommand, Unit> {

  private final AirportReadRepository airportReadRepository;

  public CreateAirportMongoCommandHandler(AirportReadRepository airportReadRepository) {
    this.airportReadRepository = airportReadRepository;
  }

  public Unit handle(CreateAirportMongoCommand command) {

    AirportDocument airportDocument = Mappings.toAirportDocument(command);

    var airportExist = airportReadRepository.findByAirportIdAndIsDeletedFalse(airportDocument.getAirportId());

    if (airportExist != null) {
      throw new AirportAlreadyExistException();
    }

    airportReadRepository.save(airportDocument);

    return Unit.VALUE;
  }
}

