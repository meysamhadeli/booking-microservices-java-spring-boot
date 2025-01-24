package io.bookingmicroservices.flight.airports.features.createairport;

import buildingblocks.mediator.abstractions.commands.ICommandHandler;
import io.bookingmicroservices.flight.airports.dtos.AirportDto;
import io.bookingmicroservices.flight.airports.exceptions.AirportAlreadyExistException;
import io.bookingmicroservices.flight.airports.features.Mappings;
import io.bookingmicroservices.flight.airports.models.Airport;
import io.bookingmicroservices.flight.airports.valueobjects.Address;
import io.bookingmicroservices.flight.airports.valueobjects.AirportId;
import io.bookingmicroservices.flight.airports.valueobjects.Code;
import io.bookingmicroservices.flight.airports.valueobjects.Name;
import io.bookingmicroservices.flight.data.jpa.entities.AirportEntity;
import io.bookingmicroservices.flight.data.jpa.repositories.AirportRepository;
import org.springframework.stereotype.Service;

@Service
public class CreateAirportCommandHandler implements ICommandHandler<CreateAirportCommand, AirportDto> {
  private final AirportRepository airportRepository;

  public CreateAirportCommandHandler(
    AirportRepository airportRepository) {
    this.airportRepository = airportRepository;
  }

  @Override
  public AirportDto handle(CreateAirportCommand command) {

    AirportEntity existAirport = airportRepository.findAirportByCodeAndIsDeletedFalse(command.code());
    if (existAirport != null) {
      throw new AirportAlreadyExistException();
    }

    Airport airport = Airport.create(
      new AirportId(command.id()),
      new Name(command.name()),
      new Code(command.code()),
      new Address(command.address())
    );

    AirportEntity airportEntity = Mappings.toAirportEntity(airport);

    AirportEntity airportCreated = airportRepository.save(airportEntity);
    return Mappings.toAirportDto(airportCreated);
  }
}
