package io.bookingmicroservices.flight.seats.features.getavailableseats;

import buildingblocks.mediator.abstractions.queries.IQuery;
import io.bookingmicroservices.flight.seats.dtos.SeatDto;
import java.util.List;
import java.util.UUID;


public record GetAvailableSeatsQuery(
  UUID flightId
) implements IQuery<List<SeatDto>> {
}


