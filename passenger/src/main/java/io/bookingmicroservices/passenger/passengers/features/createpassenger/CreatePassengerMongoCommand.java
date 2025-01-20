package io.bookingmicroservices.passenger.passengers.features.createpassenger;

import buildingblocks.core.event.InternalCommand;
import buildingblocks.mediator.abstractions.commands.ICommand;
import buildingblocks.mediator.abstractions.requests.Unit;
import io.bookingmicroservices.passenger.passengers.enums.PassengerType;
import java.util.UUID;

public record CreatePassengerMongoCommand(
        UUID id,
        String name,
        String passportNumber,
        PassengerType passengerType,
        int age,
        boolean isDeleted) implements ICommand<Unit>, InternalCommand {
}
