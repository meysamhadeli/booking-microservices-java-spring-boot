package io.bookingmicroservices.flight.aircrafts.features.createaircraft;

import buildingblocks.mediator.abstractions.commands.ICommandHandler;
import io.bookingmicroservices.flight.aircrafts.dtos.AircraftDto;
import io.bookingmicroservices.flight.aircrafts.exceptions.AircraftAlreadyExistException;
import io.bookingmicroservices.flight.aircrafts.features.Mappings;
import io.bookingmicroservices.flight.aircrafts.models.Aircraft;
import io.bookingmicroservices.flight.data.jpa.repositories.AircraftRepository;
import org.springframework.stereotype.Service;

@Service
public class CreateAircraftCommandHandler implements ICommandHandler<CreateAircraftCommand, AircraftDto> {

  private final AircraftRepository aircraftRepository;

  public CreateAircraftCommandHandler(AircraftRepository aircraftRepository) {
    this.aircraftRepository = aircraftRepository;
  }

  @Override
  public AircraftDto handle(CreateAircraftCommand command) {

    boolean exists = aircraftRepository.existsByModel(command.model());
    if (exists) {
      throw new AircraftAlreadyExistException();
    }

    Aircraft aircraft = Mappings.toAircraftAggregate(command);

    Aircraft result = aircraftRepository.create(aircraft);
    return Mappings.toAircraftDto(result);
  }
}
