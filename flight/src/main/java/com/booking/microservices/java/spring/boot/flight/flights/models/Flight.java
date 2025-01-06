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

@EqualsAndHashCode(callSuper = true)
@Setter(AccessLevel.PRIVATE)
@Getter
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


    public Flight(FlightId flightId, FlightNumber flightNumber, AircraftId aircraftId, AirportId departureAirportId, AirportId arriveAirportId, DurationMinutes durationMinutes, FlightStatus status, Price price, ArriveDate arriveDate, DepartureDate departureDate, FlightDate flightDate) {
        this.setId(flightId);
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
        var flight = new Flight(id, flightNumber, aircraftId, departureAirportId, arriveAirportId, durationMinutes, status, price, arriveDate, departureDate, flightDate);

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
        this.flightNumber = flightNumber;
        this.aircraftId = aircraftId;
        this.departureAirportId = departureAirportId;
        this.departureDate = departureDate;
        this.arriveDate = arriveDate;
        this.arriveAirportId = arriveAirportId;
        this.durationMinutes = durationMinutes;
        this.flightDate = flightDate;
        this.status = status;
        this.price = price;

        addDomainEvent(new FlightUpdatedDomainEvent(
                id.value(), flightNumber.value(), aircraftId.value(), departureAirportId.value(), departureDate.value(),
                arriveDate.value(), arriveAirportId.value(), durationMinutes.value(), flightDate.value(), status, price.value(), isDeleted
        ));
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
            boolean isDeleted) {

        addDomainEvent(new FlightDeletedDomainEvent(
                id.value(), flightNumber.value(), aircraftId.value(), departureAirportId.value(), departureDate.value(),
                arriveDate.value(), arriveAirportId.value(), durationMinutes.value(), flightDate.value(), status, price.value(), isDeleted
        ));
    }
}
