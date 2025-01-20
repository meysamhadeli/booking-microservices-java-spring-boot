package io.bookingmicroservices.flight.flights.models;

import buildingblocks.core.model.AggregateRoot;
import io.bookingmicroservices.flight.aircrafts.valueobjects.AircraftId;
import io.bookingmicroservices.flight.airports.valueobjects.AirportId;
import io.bookingmicroservices.flight.flights.enums.FlightStatus;
import io.bookingmicroservices.flight.flights.features.createflight.FlightCreatedDomainEvent;
import io.bookingmicroservices.flight.flights.features.deleteflight.FlightDeletedDomainEvent;
import io.bookingmicroservices.flight.flights.features.updateflight.FlightUpdatedDomainEvent;
import io.bookingmicroservices.flight.flights.valueobjects.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Getter
@Setter(AccessLevel.PRIVATE)
public class Flight extends AggregateRoot<FlightId> {
    FlightNumber flightNumber;
    AircraftId aircraftId;
    AirportId departureAirportId;
    AirportId arriveAirportId;
    DurationMinutes durationMinutes;
    FlightStatus status;
    Price price;
    ArriveDate arriveDate;
    DepartureDate departureDate;
    FlightDate flightDate;
    boolean isDeleted;

    public Flight(FlightId flightId, FlightNumber flightNumber, AircraftId aircraftId, AirportId departureAirportId, AirportId arriveAirportId, DurationMinutes durationMinutes, FlightStatus status, Price price, ArriveDate arriveDate, DepartureDate departureDate, FlightDate flightDate, boolean isDeleted) {
        this.id = flightId;
        this.flightNumber = flightNumber;
        this.aircraftId = aircraftId;
        this.departureAirportId = departureAirportId;
        this.arriveAirportId = arriveAirportId;
        this.durationMinutes = durationMinutes;
        this.status = status;
        this.price = price;
        this.arriveDate = arriveDate;
        this.departureDate = departureDate;
        this.flightDate = flightDate;
        this.isDeleted = isDeleted;
    }

    public static Flight create(
            FlightId id,
            FlightNumber flightNumber,
            AircraftId aircraftId,
            AirportId departureAirportId,
            DepartureDate departureDate,
            ArriveDate arriveDate,
            AirportId arriveAirportId,
            DurationMinutes durationMinutes,
            FlightDate flightDate,
            FlightStatus status,
            Price price,
            boolean isDeleted
    ) {
        var flight = new Flight(id, flightNumber, aircraftId, departureAirportId, arriveAirportId, durationMinutes, status, price, arriveDate, departureDate, flightDate, isDeleted);

        flight.addDomainEvent(new FlightCreatedDomainEvent(
                flight.getId().value(),
                flight.flightNumber.value(),
                flight.aircraftId.value(),
                flight.departureAirportId.value(),
                flight.departureDate.value(),
                flight.arriveDate.value(),
                flight.arriveAirportId.value(),
                flight.durationMinutes.value(),
                flight.flightDate.value(),
                flight.status,
                flight.price.value(),
                isDeleted
        ));

        return flight;
    }

  public void update(
    FlightId id,
    FlightNumber flightNumber,
    AircraftId aircraftId,
    AirportId departureAirportId,
    DepartureDate departureDate,
    ArriveDate arriveDate,
    AirportId arriveAirportId,
    DurationMinutes durationMinutes,
    FlightDate flightDate,
    FlightStatus status,
    Price price,
    boolean isDeleted
  ) {

    this.id = id;
    this.flightNumber = flightNumber;
    this.aircraftId = aircraftId;
    this.departureAirportId = departureAirportId;
    this.arriveAirportId = arriveAirportId;
    this.durationMinutes = durationMinutes;
    this.status = status;
    this.price = price;
    this.arriveDate = arriveDate;
    this.departureDate = departureDate;
    this.flightDate = flightDate;
    this.isDeleted = isDeleted;

    this.addDomainEvent(new FlightUpdatedDomainEvent(id.value(), flightNumber.value(), aircraftId.value(), departureAirportId.value(), departureDate.value(),
      arriveDate.value(), arriveAirportId.value(), durationMinutes.value(), flightDate.value(), status, price.value(), isDeleted));
  }

    public void delete(
      FlightId id,
      FlightNumber flightNumber,
      AircraftId aircraftId,
      AirportId departureAirportId,
      DepartureDate departureDate,
      ArriveDate arriveDate,
      AirportId arriveAirportId,
      DurationMinutes durationMinutes,
      FlightDate flightDate,
      FlightStatus status,
      Price price,
      boolean isDeleted
    ) {

      this.id = id;
      this.flightNumber = flightNumber;
      this.aircraftId = aircraftId;
      this.departureAirportId = departureAirportId;
      this.arriveAirportId = arriveAirportId;
      this.durationMinutes = durationMinutes;
      this.status = status;
      this.price = price;
      this.arriveDate = arriveDate;
      this.departureDate = departureDate;
      this.flightDate = flightDate;
      this.isDeleted = isDeleted;

      this.addDomainEvent(new FlightDeletedDomainEvent(id.value(), flightNumber.value(), aircraftId.value(), departureAirportId.value(), departureDate.value(),
        arriveDate.value(), arriveAirportId.value(), durationMinutes.value(), flightDate.value(), status, price.value(), isDeleted));
    }
}
