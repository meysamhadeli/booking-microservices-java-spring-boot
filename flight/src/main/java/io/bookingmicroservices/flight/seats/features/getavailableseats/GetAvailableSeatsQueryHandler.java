package io.bookingmicroservices.flight.seats.features.getavailableseats;

import buildingblocks.mediator.abstractions.queries.IQueryHandler;
import io.bookingmicroservices.flight.data.mongo.documents.SeatDocument;
import io.bookingmicroservices.flight.data.mongo.repositories.SeatReadRepository;
import io.bookingmicroservices.flight.seats.dtos.SeatDto;
import io.bookingmicroservices.flight.seats.features.Mappings;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class GetAvailableSeatsQueryHandler implements IQueryHandler<GetAvailableSeatsQuery, List<SeatDto>> {
  private final SeatReadRepository seatReadRepository;

  public GetAvailableSeatsQueryHandler(SeatReadRepository seatReadRepository) {
    this.seatReadRepository = seatReadRepository;
  }

  @Override
  public List<SeatDto> handle(GetAvailableSeatsQuery query) {
    List<SeatDocument> seats = seatReadRepository.findAllSeatsByFlightIdAndIsDeletedFalse(query.flightId());
    return seats.stream().map(Mappings::toSeatDto).toList();
  }
}

