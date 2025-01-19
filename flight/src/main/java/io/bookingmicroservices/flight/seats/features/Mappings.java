package io.bookingmicroservices.flight.seats.features;

import com.github.f4b6a3.uuid.UuidCreator;
import io.bookingmicroservices.flight.aircrafts.valueobjects.AircraftId;
import io.bookingmicroservices.flight.aircrafts.valueobjects.ManufacturingYear;
import io.bookingmicroservices.flight.aircrafts.valueobjects.Model;
import io.bookingmicroservices.flight.aircrafts.valueobjects.Name;
import io.bookingmicroservices.flight.data.jpa.entities.SeatEntity;
import io.bookingmicroservices.flight.data.mongo.entities.SeatDocument;
import io.bookingmicroservices.flight.seats.dtos.SeatDto;
import io.bookingmicroservices.flight.seats.features.createseat.CreateSeatCommand;
import io.bookingmicroservices.flight.seats.features.createseat.CreateSeatMongoCommand;
import io.bookingmicroservices.flight.seats.features.createseat.CreateSeatRequestDto;
import io.bookingmicroservices.flight.seats.models.Seat;
import io.bookingmicroservices.flight.seats.valueobjects.FlightId;
import io.bookingmicroservices.flight.seats.valueobjects.SeatId;
import io.bookingmicroservices.flight.seats.valueobjects.SeatNumber;

public final class Mappings {

  public static SeatEntity toSeatEntity(Seat seat) {
    return new SeatEntity(
      seat.getId().value(),
      seat.getSeatNumber().value(),
      seat.getSeatType(),
      seat.getSeatClass(),
      seat.getFlightId().value()
    );
  }

  public static Seat toSeatAggregate(SeatEntity seatEntity) {
    return new Seat(
      new SeatId(seatEntity.getId()),
      new SeatNumber(seatEntity.getSeatNumber()),
      seatEntity.getType(),
      seatEntity.getSeatClass(),
      new FlightId(seatEntity.getFlightId()),
      seatEntity.isDeleted()
    );
  }

  public static Seat toSeatAggregate(CreateSeatCommand createSeatCommand) {
    return Seat.create(
     new SeatId(createSeatCommand.id()),
      new SeatNumber(createSeatCommand.seatNumber()),
      createSeatCommand.seatType(),
      createSeatCommand.seatClass(),
      new FlightId(createSeatCommand.flightId()),
      false
    );
  }

  public static SeatDto toSeatDto(Seat seat) {
    return new SeatDto(
      seat.getId().value(),
      seat.getSeatNumber().value(),
      seat.getSeatType(),
      seat.getSeatClass(),
      seat.getFlightId().value());
  }

  public static CreateSeatCommand toCreateSeatCommand(CreateSeatRequestDto createSeatRequestDto) {
    return new CreateSeatCommand(
      UuidCreator.getTimeOrderedEpoch(),
      createSeatRequestDto.seatNumber(),
      createSeatRequestDto.seatType(),
      createSeatRequestDto.seatClass(),
      createSeatRequestDto.flightId()
    );
  }

  public static SeatDocument toSeatDocument(CreateSeatMongoCommand createSeatMongoCommand) {
    return new SeatDocument(
      createSeatMongoCommand.id(),
      createSeatMongoCommand.seatNumber(),
      createSeatMongoCommand.seatType(),
      createSeatMongoCommand.seatClass(),
      createSeatMongoCommand.flightId(),
      createSeatMongoCommand.isDeleted()
    );
  }

  public static SeatDocument toSeatDocument(SeatEntity seatEntity) {
    return new SeatDocument(
      seatEntity.getId(),
      seatEntity.getSeatNumber(),
      seatEntity.getType(),
      seatEntity.getSeatClass(),
      seatEntity.getFlightId(),
      seatEntity.isDeleted()
    );
  }
}
