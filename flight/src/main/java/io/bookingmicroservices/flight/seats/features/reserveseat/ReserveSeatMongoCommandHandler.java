package io.bookingmicroservices.flight.seats.features.reserveseat;

import buildingblocks.mediator.abstractions.commands.ICommandHandler;
import buildingblocks.mediator.abstractions.requests.Unit;
import io.bookingmicroservices.flight.data.mongo.documents.SeatDocument;
import io.bookingmicroservices.flight.data.mongo.repositories.SeatReadRepository;
import io.bookingmicroservices.flight.seats.exceptions.SeatAlreadyExistException;
import io.bookingmicroservices.flight.seats.exceptions.SeatNumberAlreadyReservedException;
import io.bookingmicroservices.flight.seats.features.Mappings;
import org.springframework.stereotype.Service;

@Service
public class ReserveSeatMongoCommandHandler implements ICommandHandler<ReserveSeatMongoCommand, Unit> {
  private final SeatReadRepository seatReadRepository;

  public ReserveSeatMongoCommandHandler(SeatReadRepository seatReadRepository) {
    this.seatReadRepository = seatReadRepository;
  }

  @Override
  public Unit handle(ReserveSeatMongoCommand command) {
    SeatDocument existSeat = seatReadRepository.findSeatByFlightIdAndSeatNumberAndIsDeletedFalse(command.flightId(), command.seatNumber());

    if (existSeat == null) {
      throw new SeatNumberAlreadyReservedException();
    }

    SeatDocument reservedSeatDocument = Mappings.toReserveSeatDocument(existSeat);
    seatReadRepository.save(reservedSeatDocument);

    return Unit.VALUE;
  }
}
