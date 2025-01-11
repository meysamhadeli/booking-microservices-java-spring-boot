package com.booking.microservices.java.spring.boot.flight.flights.features.create;

import com.booking.microservices.java.spring.boot.flight.aircrafts.valueobjects.AircraftId;
import com.booking.microservices.java.spring.boot.flight.airports.valueobjects.AirportId;
import com.booking.microservices.java.spring.boot.flight.data.jpa.entities.FlightEntity;
import com.booking.microservices.java.spring.boot.flight.flights.dtos.FlightDto;
import com.booking.microservices.java.spring.boot.flight.flights.features.create.flight.CreateFlightCommand;
import com.booking.microservices.java.spring.boot.flight.flights.features.create.flight.CreateFlightRequestDto;
import com.booking.microservices.java.spring.boot.flight.flights.models.Flight;
import com.booking.microservices.java.spring.boot.flight.flights.valueobjects.*;
import com.github.f4b6a3.uuid.UuidCreator;

public final class Mappings {

  public static FlightEntity toFlightEntity(Flight flight) {
    return new FlightEntity(
      flight.getId().value(),
      flight.getFlightNumber().value(),
      flight.getAircraftId().value(),
      flight.getDepartureAirportId().value(),
      flight.getArriveAirportId().value(),
      flight.getDurationMinutes().value(),
      flight.getStatus(),
      flight.getPrice().value(),
      flight.getArriveDate().value(),
      flight.getDepartureDate().value(),
      flight.getFlightDate().value()
    );
  }

  public static Flight toFlightAggregate(FlightEntity flightEntity) {
    return new Flight(
      new FlightId(flightEntity.getId()),
      new FlightNumber(flightEntity.getFlightNumber()),
      new AircraftId(flightEntity.getAircraftId()),
      new AirportId(flightEntity.getArriveAirportId()),
      new AirportId(flightEntity.getDepartureAirportId()),
      new DurationMinutes(flightEntity.getDurationMinutes()),
      flightEntity.getStatus(),
      new Price(flightEntity.getPrice()),
      new ArriveDate(flightEntity.getArriveDate()),
      new DepartureDate(flightEntity.getDepartureDate()),
      new FlightDate(flightEntity.getFlightDate()),
      flightEntity.isDeleted()
    );
  }

  public static CreateFlightCommand toCreateFlightCommand(CreateFlightRequestDto createFlightRequestDto) {
    return new CreateFlightCommand(
      UuidCreator.getTimeOrderedEpoch(),
      createFlightRequestDto.flightNumber(),
      createFlightRequestDto.aircraftId(),
      createFlightRequestDto.departureAirportId(),
      createFlightRequestDto.departureDate(),
      createFlightRequestDto.arriveDate(),
      createFlightRequestDto.arriveAirportId(),
      createFlightRequestDto.durationMinutes(),
      createFlightRequestDto.flightDate(),
      createFlightRequestDto.status(),
      createFlightRequestDto.price()
    );
  }

  public static Flight toFlightAggregate(CreateFlightCommand createFlightCommand) {
   return Flight.create(
      new FlightId(createFlightCommand.id()),
      new FlightNumber(createFlightCommand.flightNumber()),
      new AircraftId(createFlightCommand.aircraftId()),
      new AirportId(createFlightCommand.departureAirportId()),
      new DepartureDate(createFlightCommand.departureDate()),
      new ArriveDate(createFlightCommand.arriveDate()),
      new AirportId(createFlightCommand.arriveAirportId()),
      new DurationMinutes(createFlightCommand.durationMinutes()),
      new FlightDate(createFlightCommand.flightDate()),
     createFlightCommand.status(),
      new Price(createFlightCommand.price()),
      false
    );
  }

  public static FlightDto toFlightDto(Flight flight) {
    return new FlightDto(
      flight.getId().value(),
      flight.getFlightNumber().value(),
      flight.getAircraftId().value(),
      flight.getDepartureAirportId().value(),
      flight.getDepartureDate().value(),
      flight.getArriveDate().value(),
      flight.getArriveAirportId().value(),
      flight.getDurationMinutes().value(),
      flight.getFlightDate().value(),
      flight.getStatus(),
      flight.getPrice().value());
  }
}
