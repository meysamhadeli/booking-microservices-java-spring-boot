package io.bookingmicroservices.flight.flights.features.updateflight;

import buildingblocks.mediator.abstractions.commands.ICommandHandler;
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

    Flight flight = flightRepository.findFlightByIdAndIsDeletedFalse(command.id());
    if (flight == null) {
      throw new FlightNotFoundException();
    }

    flight.update(flight.getId(), flight.getFlightNumber(), flight.getAircraftId(), flight.getDepartureAirportId(), flight.getDepartureDate(), flight.getArriveDate(),
      flight.getArriveAirportId(), flight.getDurationMinutes(), flight.getFlightDate(), flight.getStatus(), flight.getPrice(), flight.isDeleted());

    Flight updatedFlight = flightRepository.update(flight);
    return Mappings.toFlightDto(updatedFlight);
  }
}
