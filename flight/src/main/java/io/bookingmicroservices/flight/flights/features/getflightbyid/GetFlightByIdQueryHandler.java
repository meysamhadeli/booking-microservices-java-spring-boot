package io.bookingmicroservices.flight.flights.features.getflightbyid;

import buildingblocks.mediator.abstractions.queries.IQueryHandler;
import io.bookingmicroservices.flight.data.mongo.documents.FlightDocument;
import io.bookingmicroservices.flight.data.mongo.repositories.FlightReadRepository;
import io.bookingmicroservices.flight.flights.dtos.FlightDto;
import io.bookingmicroservices.flight.flights.exceptions.FlightNotFoundException;
import io.bookingmicroservices.flight.flights.features.Mappings;
import org.springframework.stereotype.Service;

@Service
public class GetFlightByIdQueryHandler implements IQueryHandler<GetFlightByIdQuery, FlightDto> {
  private final FlightReadRepository flightReadRepository;

  public GetFlightByIdQueryHandler(FlightReadRepository flightReadRepository) {
    this.flightReadRepository = flightReadRepository;
  }

  @Override
  public FlightDto handle(GetFlightByIdQuery query) {
    FlightDocument flightDocument = flightReadRepository.findByFlightIdAndIsDeletedFalse(query.id());

    if (flightDocument == null) {
      throw new FlightNotFoundException();
    }

    return Mappings.toFlightDto(flightDocument);
  }
}
