package io.bookingmicroservices.flight.seats.features.reserveseat;

import buildingblocks.mediator.abstractions.commands.ICommandHandler;
import io.bookingmicroservices.flight.data.jpa.repositories.SeatRepository;
import io.bookingmicroservices.flight.seats.dtos.SeatDto;
import io.bookingmicroservices.flight.seats.exceptions.SeatNumberAlreadyReservedException;
import io.bookingmicroservices.flight.seats.features.Mappings;
import io.bookingmicroservices.flight.seats.models.Seat;
import org.springframework.stereotype.Service;


@Service
public class ReserveSeatCommandHandler implements ICommandHandler<ReserveSeatCommand, SeatDto> {
  private final SeatRepository seatRepository;

  public ReserveSeatCommandHandler(SeatRepository seatRepository) {
    this.seatRepository = seatRepository;
  }

  @Override
  public SeatDto handle(ReserveSeatCommand command) {
    Seat seat = seatRepository.findSeatByFlightIdAndSeatNumberAndIsDeletedFalse(command.flightId(), command.seatNumber());

    if (seat == null) {
         throw new SeatNumberAlreadyReservedException();
    }

    seat.reserveSeat();
    Seat seatAggregate = seatRepository.update(seat);

    return Mappings.toSeatDto(seatAggregate);
  }
}
