package io.bookingmicroservices.flight.seats.features;

import com.github.f4b6a3.uuid.UuidCreator;
import flight.Flight;
import io.bookingmicroservices.flight.data.jpa.entities.SeatEntity;
import io.bookingmicroservices.flight.data.mongo.documents.SeatDocument;
import io.bookingmicroservices.flight.seats.dtos.SeatDto;
import io.bookingmicroservices.flight.seats.enums.SeatClass;
import io.bookingmicroservices.flight.seats.enums.SeatType;
import io.bookingmicroservices.flight.seats.features.createseat.CreateSeatCommand;
import io.bookingmicroservices.flight.seats.features.createseat.CreateSeatMongoCommand;
import io.bookingmicroservices.flight.seats.features.createseat.CreateSeatRequestDto;
import io.bookingmicroservices.flight.seats.features.reserveseat.ReserveSeatCommand;
import io.bookingmicroservices.flight.seats.features.reserveseat.ReserveSeatMongoCommand;
import io.bookingmicroservices.flight.seats.features.reserveseat.ReserveSeatRequestDto;
import io.bookingmicroservices.flight.seats.models.Seat;
import io.bookingmicroservices.flight.seats.valueobjects.SeatId;

public final class Mappings {

  public static SeatEntity toSeatEntity(Seat seat) {
    return new SeatEntity(
      seat.getId().getSeatId(),
      seat.getSeatNumber(),
      seat.getSeatType(),
      seat.getSeatClass(),
      seat.getFlightId(),
      seat.getCreatedAt(),
      seat.getCreatedBy(),
      seat.getLastModified(),
      seat.getLastModifiedBy(),
      seat.getVersion(),
      seat.isDeleted()
    );
  }

  public static Seat toSeatAggregate(SeatEntity seatEntity) {
    return new Seat(
      new SeatId(seatEntity.getId()),
      seatEntity.getSeatNumber(),
      seatEntity.getType(),
      seatEntity.getSeatClass(),
      seatEntity.getFlightId(),
      seatEntity.getCreatedAt(),
      seatEntity.getCreatedBy(),
      seatEntity.getLastModified(),
      seatEntity.getLastModifiedBy(),
      seatEntity.getVersion(),
      seatEntity.isDeleted()
    );
  }

  public static SeatDto toSeatDto(SeatEntity seatEntity) {
    return new SeatDto(
      seatEntity.getId(),
      seatEntity.getSeatNumber().getSeatNumber(),
      seatEntity.getType(),
      seatEntity.getSeatClass(),
      seatEntity.getFlightId().getFlightId());
  }

  public static SeatDto toSeatDto(SeatDocument seatDocument) {
    return new SeatDto(
      seatDocument.getSeatId(),
      seatDocument.getSeatNumber(),
      seatDocument.getType(),
      seatDocument.getSeatClass(),
      seatDocument.getFlightId()
    );
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


  public static ReserveSeatCommand toReserveSeatCommand(ReserveSeatRequestDto reserveSeatRequestDto) {
    return new ReserveSeatCommand(
      reserveSeatRequestDto.seatNumber(),
      reserveSeatRequestDto.flightId()
    );
  }

  public static SeatDocument toSeatDocument(ReserveSeatMongoCommand reserveSeatMongoCommand) {
    return new SeatDocument(
      reserveSeatMongoCommand.id(),
      reserveSeatMongoCommand.seatNumber(),
      reserveSeatMongoCommand.seatType(),
      reserveSeatMongoCommand.seatClass(),
      reserveSeatMongoCommand.flightId(),
      reserveSeatMongoCommand.isDeleted()
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

  public static SeatDocument toReserveSeatDocument(SeatDocument seatDocument) {
    return new SeatDocument(
      seatDocument.getId(),
      seatDocument.getSeatId(),
      seatDocument.getSeatNumber(),
      seatDocument.getType(),
      seatDocument.getSeatClass(),
      seatDocument.getFlightId(),
      true
    );
  }


  public static SeatDocument toSeatDocument(SeatEntity seatEntity) {
    return new SeatDocument(
      seatEntity.getId(),
      seatEntity.getSeatNumber().getSeatNumber(),
      seatEntity.getType(),
      seatEntity.getSeatClass(),
      seatEntity.getFlightId().getFlightId(),
      seatEntity.isDeleted()
    );
  }

  public static Flight.SeatResponseDto toSeatResponseDtoGrpc(SeatDto seatDto) {
    return Flight.SeatResponseDto.newBuilder()
      .setId(seatDto.id().toString())
      .setSeatNumber(seatDto.seatNumber())
      .setSeatType(toSeatTypeGrpc(seatDto.seatType()))
      .setSeatClass(toSeatClassGrpc(seatDto.seatClass()))
      .setFlightId(seatDto.flightId().toString())
      .build();
  }

  public static Flight.SeatClass toSeatClassGrpc(SeatClass seatClass) {
    return switch (seatClass) {
      case FirstClass -> Flight.SeatClass.SEAT_CLASS_FIRST_CLASS;
      case Business -> Flight.SeatClass.SEAT_CLASS_BUSINESS;
      case Economy -> Flight.SeatClass.SEAT_CLASS_ECONOMY;
    };
  }

  public static Flight.SeatType toSeatTypeGrpc(SeatType seatType) {
    return switch (seatType) {
      case Aisle -> Flight.SeatType.SEAT_TYPE_AISLE;
      case Middle -> Flight.SeatType.SEAT_TYPE_MIDDLE;
      case Window -> Flight.SeatType.SEAT_TYPE_WINDOW;
    };
  }
}
