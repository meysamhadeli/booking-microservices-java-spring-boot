package com.booking.microservices.java.spring.boot.flight.flights.models;

import buildingblocks.core.model.AggregateRoot;
import com.booking.microservices.java.spring.boot.flight.aircrafts.valueobjects.AircraftId;
import com.booking.microservices.java.spring.boot.flight.airports.valueobjects.AirportId;
import com.booking.microservices.java.spring.boot.flight.flights.enums.FlightStatus;
import com.booking.microservices.java.spring.boot.flight.flights.events.FlightDeletedDomainEvent;
import com.booking.microservices.java.spring.boot.flight.flights.events.FlightUpdatedDomainEvent;
import com.booking.microservices.java.spring.boot.flight.flights.features.create.flight.FlightCreatedDomainEvent;
import com.booking.microservices.java.spring.boot.flight.flights.valueobjects.*;
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
    boolean deleted;

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
        this.deleted = isDeleted;
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

  public Flight update(
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

    Flight updatedFlight = new Flight(
      this.getId(),
      flightNumber,
      aircraftId,
      departureAirportId,
      arriveAirportId,
      durationMinutes,
      status,
      price,
      arriveDate,
      departureDate,
      flightDate,
      isDeleted
    );

    // Add the domain event for the update
    updatedFlight.addDomainEvent(new FlightUpdatedDomainEvent(
      updatedFlight.getId().value(),
      updatedFlight.flightNumber.value(),
      updatedFlight.aircraftId.value(),
      updatedFlight.departureAirportId.value(),
      updatedFlight.departureDate.value(),
      updatedFlight.arriveDate.value(),
      updatedFlight.arriveAirportId.value(),
      updatedFlight.durationMinutes.value(),
      updatedFlight.flightDate.value(),
      updatedFlight.status,
      updatedFlight.price.value(),
      isDeleted
    ));

    return updatedFlight;
  }

    public void delete() {

       this.deleted = true;

        addDomainEvent(new FlightDeletedDomainEvent(
                this.getId().value(), this.flightNumber.value(), this.aircraftId.value(), this.departureAirportId.value(), this.departureDate.value(),
                this.arriveDate.value(), this.arriveAirportId.value(), this.durationMinutes.value(), this.flightDate.value(), this.status, this.price.value(), true
        ));
    }
}
