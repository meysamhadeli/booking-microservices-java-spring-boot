package io.bookingmicroservices.flight.seats.features.createseat;

import buildingblocks.mediator.abstractions.commands.ICommandHandler;
import io.bookingmicroservices.flight.data.jpa.repositories.SeatRepository;
import io.bookingmicroservices.flight.seats.dtos.SeatDto;
import io.bookingmicroservices.flight.seats.exceptions.SeatAlreadyExistException;
import io.bookingmicroservices.flight.seats.features.Mappings;
import io.bookingmicroservices.flight.seats.models.Seat;
import org.springframework.stereotype.Service;

@Service
public class CrateSeatCommandHandler implements ICommandHandler<CreateSeatCommand, SeatDto> {

  private final SeatRepository seatRepository;

  public CrateSeatCommandHandler(SeatRepository seatRepository) {
    this.seatRepository = seatRepository;
  }

  @Override
  public SeatDto handle(CreateSeatCommand command) {

    boolean exists = seatRepository.existsById(command.id());
    if (exists) {
      throw new SeatAlreadyExistException();
    }

    Seat seat = Mappings.toSeatAggregate(command);

    Seat result = seatRepository.create(seat);
    return Mappings.toSeatDto(result);
  }
}
