package io.bookingmicroservices.flight.flights.features.getavailableflights;

import buildingblocks.mediator.abstractions.queries.IQuery;
import io.bookingmicroservices.flight.flights.dtos.FlightDto;
import java.util.List;

public record GetAvailableFlightsQuery() implements IQuery<List<FlightDto>> {
}


