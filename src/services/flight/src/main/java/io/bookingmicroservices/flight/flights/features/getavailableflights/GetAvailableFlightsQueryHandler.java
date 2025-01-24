package io.bookingmicroservices.flight.flights.features.getavailableflights;

import buildingblocks.mediator.abstractions.queries.IQueryHandler;
import io.bookingmicroservices.flight.data.mongo.documents.FlightDocument;
import io.bookingmicroservices.flight.data.mongo.repositories.FlightReadRepository;
import io.bookingmicroservices.flight.flights.dtos.FlightDto;
import io.bookingmicroservices.flight.flights.features.Mappings;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetAvailableFlightsQueryHandler implements IQueryHandler<GetAvailableFlightsQuery, List<FlightDto>> {
  private final FlightReadRepository flightReadRepository;

  public GetAvailableFlightsQueryHandler(FlightReadRepository flightReadRepository) {
    this.flightReadRepository = flightReadRepository;
  }

  @Override
  public List<FlightDto> handle(GetAvailableFlightsQuery query) {
    List<FlightDocument> flightDocuments =  flightReadRepository.findAllByIsDeletedFalse();
    return flightDocuments.stream().map(Mappings::toFlightDto).toList();
  }
}
