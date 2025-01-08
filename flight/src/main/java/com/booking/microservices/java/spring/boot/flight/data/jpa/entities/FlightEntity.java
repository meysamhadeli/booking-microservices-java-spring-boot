package com.booking.microservices.java.spring.boot.flight.data.jpa.entities;

import buildingblocks.core.model.BaseEntity;
import com.booking.microservices.java.spring.boot.flight.aircrafts.valueobjects.AircraftId;
import com.booking.microservices.java.spring.boot.flight.airports.valueobjects.AirportId;
import com.booking.microservices.java.spring.boot.flight.flights.enums.FlightStatus;
import com.booking.microservices.java.spring.boot.flight.flights.models.Flight;
import com.booking.microservices.java.spring.boot.flight.flights.valueobjects.*;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "flights")
public class FlightEntity extends BaseEntity<UUID> {

    private String flightNumber;

    private UUID aircraftId;

    private UUID departureAirportId;

    private LocalDateTime departureDate;

    private LocalDateTime arriveDate;

    private UUID arriveAirportId;

    private BigDecimal durationMinutes;

    private LocalDateTime flightDate;

    @Enumerated(EnumType.STRING)
    private FlightStatus status;

    private BigDecimal price;

    // JPA constructor
    protected FlightEntity() {
    }

    public FlightEntity(UUID id, String flightNumber, UUID aircraftId, UUID departureAirportId, UUID arriveAirportId, BigDecimal durationMinutes, FlightStatus status, BigDecimal price, LocalDateTime arriveDate, LocalDateTime departureDate, LocalDateTime flightDate) {
        this.setId(id);;
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

    public static FlightEntity toEntity(Flight flight) {
        return new FlightEntity(
                flight.getId().value(),
                flight.getFlightNumber().value(),
                flight.getAircraftId().value(),
                flight.getDepartureAirportId().value(),
                flight.getArriveAirportId().value(),
                flight.getDurationMinutes().value(),
                flight.getStatus(),
                flight.getDurationMinutes().value(),
                flight.getArriveDate().value(),
                flight.getDepartureDate().value(),
                flight.getFlightDate().value()
        );
    }

    public Flight toAggregate() {
        return new Flight(
                new FlightId(this.getId()),
                new FlightNumber(this.flightNumber),
                new AircraftId(this.aircraftId),
                new AirportId(this.arriveAirportId),
                new AirportId(this.departureAirportId),
                new DurationMinutes(this.durationMinutes),
                this.status,
                new Price(this.price),
                new ArriveDate(this.arriveDate),
                new DepartureDate(this.departureDate),
                new FlightDate(this.flightDate)
        );
    }
}

