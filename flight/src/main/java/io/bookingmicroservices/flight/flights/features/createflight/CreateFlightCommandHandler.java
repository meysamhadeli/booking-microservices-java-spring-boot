package io.bookingmicroservices.flight.flights.features.createflight;

import buildingblocks.mediator.abstractions.commands.ICommandHandler;
import io.bookingmicroservices.flight.data.jpa.repositories.FlightRepository;
import io.bookingmicroservices.flight.flights.dtos.FlightDto;
import io.bookingmicroservices.flight.flights.exceptions.FlightAlreadyExistException;
import io.bookingmicroservices.flight.flights.features.Mappings;
import io.bookingmicroservices.flight.flights.models.Flight;
import org.springframework.stereotype.Service;

@Service
public class CreateFlightCommandHandler implements ICommandHandler<CreateFlightCommand, FlightDto> {
  private final FlightRepository flightRepository;

  public CreateFlightCommandHandler(
    FlightRepository flightRepository) {
    this.flightRepository = flightRepository;
  }

  @Override
  public FlightDto handle(CreateFlightCommand command) {

    boolean exists = flightRepository.existsByFlightNumber(command.flightNumber());
    if (exists) {
      throw new FlightAlreadyExistException();
    }

    Flight flight = Mappings.toFlightAggregate(command);

    Flight result = flightRepository.create(flight);
      return Mappings.toFlightDto(result);
  }
}
