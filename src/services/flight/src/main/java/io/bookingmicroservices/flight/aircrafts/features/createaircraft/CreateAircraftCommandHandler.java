package io.bookingmicroservices.flight.aircrafts.features.createaircraft;

import buildingblocks.mediator.abstractions.commands.ICommandHandler;
import io.bookingmicroservices.flight.aircrafts.dtos.AircraftDto;
import io.bookingmicroservices.flight.aircrafts.exceptions.AircraftAlreadyExistException;
import io.bookingmicroservices.flight.aircrafts.features.Mappings;
import io.bookingmicroservices.flight.aircrafts.models.Aircraft;
import io.bookingmicroservices.flight.aircrafts.valueobjects.AircraftId;
import io.bookingmicroservices.flight.aircrafts.valueobjects.ManufacturingYear;
import io.bookingmicroservices.flight.aircrafts.valueobjects.Model;
import io.bookingmicroservices.flight.aircrafts.valueobjects.Name;
import io.bookingmicroservices.flight.data.jpa.entities.AircraftEntity;
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

    AircraftEntity existAircraft = aircraftRepository.findAircraftByModelAndIsDeletedFalse(command.model());
    if (existAircraft != null) {
      throw new AircraftAlreadyExistException();
    }

    Aircraft aircraft = Aircraft.create(
      new AircraftId(command.id()),
      new Name(command.name()),
      new Model(command.model()),
      new ManufacturingYear(command.manufacturingYear())
    );

    AircraftEntity aircraftEntity = Mappings.toAircraftEntity(aircraft);

    AircraftEntity aircraftCreated = aircraftRepository.save(aircraftEntity);
    return Mappings.toAircraftDto(aircraftCreated);
  }
}
