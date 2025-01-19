package io.bookingmicroservices.flight.airports.features.createairport;

import buildingblocks.mediator.abstractions.commands.ICommandHandler;
import io.bookingmicroservices.flight.airports.dtos.AirportDto;
import io.bookingmicroservices.flight.airports.exceptions.AirportAlreadyExistException;
import io.bookingmicroservices.flight.airports.features.Mappings;
import io.bookingmicroservices.flight.airports.models.Airport;
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

      boolean exists = airportRepository.existsByCode(command.code());
      if (exists) {
        throw new AirportAlreadyExistException();
      }

      Airport airport = Mappings.toAirportAggregate(command);

      Airport result = airportRepository.create(airport);
      return Mappings.toAirportDto(result);
  }
}
