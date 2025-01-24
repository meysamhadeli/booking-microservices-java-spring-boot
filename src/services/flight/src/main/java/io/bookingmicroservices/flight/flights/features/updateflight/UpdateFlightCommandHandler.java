package io.bookingmicroservices.flight.flights.features.updateflight;

import buildingblocks.mediator.abstractions.commands.ICommandHandler;
import io.bookingmicroservices.flight.data.jpa.entities.FlightEntity;
import io.bookingmicroservices.flight.data.jpa.repositories.FlightRepository;
import io.bookingmicroservices.flight.flights.dtos.FlightDto;
import io.bookingmicroservices.flight.flights.exceptions.FlightNotFoundException;
import io.bookingmicroservices.flight.flights.features.Mappings;
import io.bookingmicroservices.flight.flights.models.Flight;
import org.springframework.stereotype.Service;

@Service
public class UpdateFlightCommandHandler implements ICommandHandler<UpdateFlightCommand, FlightDto> {
  private final FlightRepository flightRepository;

  public UpdateFlightCommandHandler(FlightRepository flightRepository) {
    this.flightRepository = flightRepository;
  }

  @Override
  public FlightDto handle(UpdateFlightCommand command) {

    FlightEntity existingFlight = flightRepository.findFlightByIdAndIsDeletedFalse(command.id());
    if (existingFlight == null) {
      throw new FlightNotFoundException();
    }

    Flight flight = Mappings.toFlightAggregate(existingFlight);

    flight.update(flight.getFlightNumber(), flight.getAircraftId(), flight.getDepartureAirportId(), flight.getDepartureDate(), flight.getArriveDate(),
      flight.getArriveAirportId(), flight.getDurationMinutes(), flight.getFlightDate(), flight.getStatus(), flight.getPrice(), flight.isDeleted());

    FlightEntity flightEntity = Mappings.toFlightEntity(flight);

    FlightEntity updatedFlight = flightRepository.save(flightEntity);
    return Mappings.toFlightDto(updatedFlight);
  }
}
