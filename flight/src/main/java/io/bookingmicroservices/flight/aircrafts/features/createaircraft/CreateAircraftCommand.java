package io.bookingmicroservices.flight.aircrafts.features.createaircraft;

import buildingblocks.core.event.InternalCommand;
import buildingblocks.mediator.abstractions.commands.ICommand;
import io.bookingmicroservices.flight.aircrafts.dtos.AircraftDto;
import java.util.UUID;

public record CreateAircraftCommand(
  UUID id,
  String name,
  String model,
  int manufacturingYear
) implements ICommand<AircraftDto>, InternalCommand {
}

