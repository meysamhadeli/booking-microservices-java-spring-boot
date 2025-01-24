package io.bookingmicroservices.flight.airports.features.createairport;

import buildingblocks.core.event.InternalCommand;
import buildingblocks.mediator.abstractions.commands.ICommand;
import buildingblocks.mediator.abstractions.requests.Unit;
import java.util.UUID;

public record CreateAirportMongoCommand(
  UUID id,
  String name,
  String code,
  String address,
  boolean isDeleted) implements ICommand<Unit>, InternalCommand {
}
