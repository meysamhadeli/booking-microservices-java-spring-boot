package io.bookingmicroservices.passenger.passengers.features.createpassenger;

import buildingblocks.core.event.InternalCommand;
import buildingblocks.mediator.abstractions.commands.ICommand;
import com.github.f4b6a3.uuid.UuidCreator;
import io.bookingmicroservices.passenger.passengers.dtos.PassengerDto;
import io.bookingmicroservices.passenger.passengers.enums.PassengerType;

import java.util.UUID;

public record CreatePassengerCommand(
        UUID id,
        String name,
        String passportNumber,
        PassengerType passengerType,
        int age
) implements ICommand<PassengerDto>, InternalCommand {
    public CreatePassengerCommand {
    }
}

