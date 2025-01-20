package io.bookingmicroservices.flight.flights.features.getflightbyid;

import buildingblocks.mediator.abstractions.queries.IQuery;
import io.bookingmicroservices.flight.flights.dtos.FlightDto;
import java.util.UUID;

public record GetFlightByIdQuery(
  UUID id
) implements IQuery<FlightDto> {
}


