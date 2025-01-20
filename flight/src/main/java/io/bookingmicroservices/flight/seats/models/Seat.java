package io.bookingmicroservices.flight.seats.models;

import buildingblocks.core.model.AggregateRoot;
import io.bookingmicroservices.flight.seats.enums.SeatClass;
import io.bookingmicroservices.flight.seats.enums.SeatType;
import io.bookingmicroservices.flight.seats.features.createseat.SeatCreatedDomainEvent;
import io.bookingmicroservices.flight.seats.features.reserveseat.SeatReservedDomainEvent;
import io.bookingmicroservices.flight.seats.valueobjects.FlightId;
import io.bookingmicroservices.flight.seats.valueobjects.SeatId;
import io.bookingmicroservices.flight.seats.valueobjects.SeatNumber;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Getter
@Setter(AccessLevel.PRIVATE)
public class Seat extends AggregateRoot<SeatId> {
  SeatNumber seatNumber;
  SeatType seatType;
  SeatClass seatClass;
  FlightId flightId;
  boolean isDeleted;

  public Seat(SeatId seatId, SeatNumber seatNumber, SeatType seatType, SeatClass seatClass, FlightId flightId, boolean isDeleted) {
    this.id = seatId;
    this.seatNumber = seatNumber;
    this.seatType = seatType;
    this.seatClass = seatClass;
    this.flightId = flightId;
    this.isDeleted = isDeleted;
  }

  public static Seat create(
    SeatId seatId,
    SeatNumber seatNumber,
    SeatType seatType,
    SeatClass seatClass,
    FlightId flightId,
    boolean isDeleted
  ) {
    var seat = new Seat(seatId, seatNumber, seatType, seatClass, flightId, isDeleted);

    seat.addDomainEvent(new SeatCreatedDomainEvent(
      seat.id.value(),
      seat.seatNumber.value(),
      seat.seatType,
      seat.seatClass,
      seat.flightId.value(),
      isDeleted
    ));

    return seat;
  }

  public void reserveSeat() {
    this.isDeleted = true;

    this.addDomainEvent(new SeatReservedDomainEvent(
      this.getId().value(),
      this.getSeatNumber().value(),
      this.getSeatType(),
      this.getSeatClass(),
      this.getFlightId().value(),
      this.isDeleted
      ));
  }
}
