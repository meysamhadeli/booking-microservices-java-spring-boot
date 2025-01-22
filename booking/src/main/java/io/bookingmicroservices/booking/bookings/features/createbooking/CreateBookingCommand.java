package io.bookingmicroservices.booking.bookings.features.createbooking;

import buildingblocks.core.event.InternalCommand;
import buildingblocks.mediator.abstractions.commands.ICommand;
import io.bookingmicroservices.booking.bookings.dtos.BookingDto;
import java.util.UUID;

public record CreateBookingCommand(
        UUID id,
        UUID passengerId,
        UUID flightId,
        String description
) implements ICommand<BookingDto>, InternalCommand {
}