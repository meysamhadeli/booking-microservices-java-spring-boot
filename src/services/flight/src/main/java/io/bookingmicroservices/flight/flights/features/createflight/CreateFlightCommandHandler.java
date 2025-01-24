package io.bookingmicroservices.flight.flights.features.createflight;

import buildingblocks.mediator.abstractions.commands.ICommandHandler;
import io.bookingmicroservices.flight.aircrafts.valueobjects.AircraftId;
import io.bookingmicroservices.flight.airports.valueobjects.AirportId;
import io.bookingmicroservices.flight.data.jpa.entities.FlightEntity;
import io.bookingmicroservices.flight.data.jpa.repositories.FlightRepository;
import io.bookingmicroservices.flight.flights.dtos.FlightDto;
import io.bookingmicroservices.flight.flights.exceptions.FlightAlreadyExistException;
import io.bookingmicroservices.flight.flights.features.Mappings;
import io.bookingmicroservices.flight.flights.models.Flight;
import io.bookingmicroservices.flight.flights.valueobjects.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CreateFlightCommandHandler implements ICommandHandler<CreateFlightCommand, FlightDto> {
  private final FlightRepository flightRepository;

  public CreateFlightCommandHandler(
    FlightRepository flightRepository) {
    this.flightRepository = flightRepository;
  }

  @Override
  public FlightDto handle(CreateFlightCommand command) {

    FlightEntity existFlight = flightRepository.findFlightByIdAndIsDeletedFalse(command.id());
    if (existFlight!= null) {
      throw new FlightAlreadyExistException();
    }

    Flight flight = Flight.create(
      new FlightId(command.id()),
      new FlightNumber(command.flightNumber()),
      new AircraftId(command.aircraftId()),
      new AirportId(command.departureAirportId()),
      new DepartureDate(command.departureDate()),
      new ArriveDate(command.arriveDate()),
      new AirportId(command.arriveAirportId()),
      new DurationMinutes(command.durationMinutes()),
      new FlightDate(command.flightDate()),
      command.status(),
      new Price(command.price())
    );

    FlightEntity flightEntity = Mappings.toFlightEntity(flight);

    FlightEntity flightCreated = flightRepository.save(flightEntity);
    return Mappings.toFlightDto(flightCreated);
  }
}
