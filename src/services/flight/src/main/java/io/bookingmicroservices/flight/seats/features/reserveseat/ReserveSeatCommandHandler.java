package io.bookingmicroservices.flight.seats.features.reserveseat;

import buildingblocks.mediator.abstractions.commands.ICommandHandler;
import io.bookingmicroservices.flight.data.jpa.entities.SeatEntity;
import io.bookingmicroservices.flight.data.jpa.repositories.SeatRepository;
import io.bookingmicroservices.flight.seats.dtos.SeatDto;
import io.bookingmicroservices.flight.seats.exceptions.SeatNumberAlreadyReservedException;
import io.bookingmicroservices.flight.seats.features.Mappings;
import io.bookingmicroservices.flight.seats.models.Seat;
import io.bookingmicroservices.flight.seats.valueobjects.FlightId;
import io.bookingmicroservices.flight.seats.valueobjects.SeatNumber;
import org.springframework.stereotype.Service;


@Service
public class ReserveSeatCommandHandler implements ICommandHandler<ReserveSeatCommand, SeatDto> {
  private final SeatRepository seatRepository;

  public ReserveSeatCommandHandler(SeatRepository seatRepository) {
    this.seatRepository = seatRepository;
  }

  @Override
  public SeatDto handle(ReserveSeatCommand command) {
    SeatEntity existSeat = seatRepository.findSeatByFlightIdAndSeatNumberAndIsDeletedFalse(new FlightId(command.flightId()), new SeatNumber(command.seatNumber()));

    if (existSeat == null) {
         throw new SeatNumberAlreadyReservedException();
    }

    Seat seat = Mappings.toSeatAggregate(existSeat);

    seat.reserveSeat();

    SeatEntity seatEntity = Mappings.toSeatEntity(seat);
    SeatEntity seatUpdated = seatRepository.save(seatEntity);

    return Mappings.toSeatDto(seatUpdated);
  }
}
