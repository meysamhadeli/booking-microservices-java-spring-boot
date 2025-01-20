package io.bookingmicroservices.flight.flights.features;

import com.github.f4b6a3.uuid.UuidCreator;
import io.bookingmicroservices.flight.aircrafts.valueobjects.AircraftId;
import io.bookingmicroservices.flight.airports.valueobjects.AirportId;
import io.bookingmicroservices.flight.data.jpa.entities.FlightEntity;
import io.bookingmicroservices.flight.data.mongo.entities.FlightDocument;
import io.bookingmicroservices.flight.flights.dtos.FlightDto;
import io.bookingmicroservices.flight.flights.features.createflight.CreateFlightCommand;
import io.bookingmicroservices.flight.flights.features.createflight.CreateFlightMongoCommand;
import io.bookingmicroservices.flight.flights.features.createflight.CreateFlightRequestDto;
import io.bookingmicroservices.flight.flights.features.deleteflight.DeleteFlightMongoCommand;
import io.bookingmicroservices.flight.flights.features.updateflight.UpdateFlightCommand;
import io.bookingmicroservices.flight.flights.features.updateflight.UpdateFlightMongoCommand;
import io.bookingmicroservices.flight.flights.features.updateflight.UpdateFlightRequestDto;
import io.bookingmicroservices.flight.flights.models.Flight;
import io.bookingmicroservices.flight.flights.valueobjects.*;

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

  public static UpdateFlightCommand toUpdateFlightCommand(UpdateFlightRequestDto updateFlightRequestDto) {
    return new UpdateFlightCommand(
      UuidCreator.getTimeOrderedEpoch(),
      updateFlightRequestDto.flightNumber(),
      updateFlightRequestDto.aircraftId(),
      updateFlightRequestDto.departureAirportId(),
      updateFlightRequestDto.departureDate(),
      updateFlightRequestDto.arriveDate(),
      updateFlightRequestDto.arriveAirportId(),
      updateFlightRequestDto.durationMinutes(),
      updateFlightRequestDto.flightDate(),
      updateFlightRequestDto.status(),
      updateFlightRequestDto.price(),
      updateFlightRequestDto.isDeleted()
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

  public static FlightDocument toFlightDocument(CreateFlightMongoCommand createFlightMongoCommand) {
    return new FlightDocument(
      createFlightMongoCommand.id(),
      createFlightMongoCommand.flightNumber(),
      createFlightMongoCommand.aircraftId(),
      createFlightMongoCommand.departureAirportId(),
      createFlightMongoCommand.arriveAirportId(),
      createFlightMongoCommand.durationMinutes(),
      createFlightMongoCommand.status(),
      createFlightMongoCommand.price(),
      createFlightMongoCommand.arriveDate(),
      createFlightMongoCommand.departureDate(),
      createFlightMongoCommand.flightDate(),
      createFlightMongoCommand.isDeleted()
    );
  }


  public static FlightDocument toFlightDocument(UpdateFlightMongoCommand updateFlightMongoCommand) {
    return new FlightDocument(
      updateFlightMongoCommand.id(),
      updateFlightMongoCommand.flightNumber(),
      updateFlightMongoCommand.aircraftId(),
      updateFlightMongoCommand.departureAirportId(),
      updateFlightMongoCommand.arriveAirportId(),
      updateFlightMongoCommand.durationMinutes(),
      updateFlightMongoCommand.status(),
      updateFlightMongoCommand.price(),
      updateFlightMongoCommand.arriveDate(),
      updateFlightMongoCommand.departureDate(),
      updateFlightMongoCommand.flightDate(),
      updateFlightMongoCommand.isDeleted()
    );
  }


  public static FlightDocument toFlightDocument(DeleteFlightMongoCommand deleteFlightMongoCommand) {
    return new FlightDocument(
      deleteFlightMongoCommand.id(),
      deleteFlightMongoCommand.flightNumber(),
      deleteFlightMongoCommand.aircraftId(),
      deleteFlightMongoCommand.departureAirportId(),
      deleteFlightMongoCommand.arriveAirportId(),
      deleteFlightMongoCommand.durationMinutes(),
      deleteFlightMongoCommand.status(),
      deleteFlightMongoCommand.price(),
      deleteFlightMongoCommand.arriveDate(),
      deleteFlightMongoCommand.departureDate(),
      deleteFlightMongoCommand.flightDate(),
      deleteFlightMongoCommand.isDeleted()
    );
  }

  public static FlightDocument toFlightDocument(FlightEntity flightEntity) {
    return new FlightDocument(
      flightEntity.getId(),
      flightEntity.getFlightNumber(),
      flightEntity.getAircraftId(),
      flightEntity.getDepartureAirportId(),
      flightEntity.getArriveAirportId(),
      flightEntity.getDurationMinutes(),
      flightEntity.getStatus(),
      flightEntity.getPrice(),
      flightEntity.getArriveDate(),
      flightEntity.getDepartureDate(),
      flightEntity.getFlightDate(),
      flightEntity.isDeleted()
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


  public static FlightDto toFlightDto(FlightDocument flightDocument) {
    return new FlightDto(
      flightDocument.getFlightId(),
      flightDocument.getFlightNumber(),
      flightDocument.getAircraftId(),
      flightDocument.getDepartureAirportId(),
      flightDocument.getDepartureDate(),
      flightDocument.getArriveDate(),
      flightDocument.getArriveAirportId(),
      flightDocument.getDurationMinutes(),
      flightDocument.getFlightDate(),
      flightDocument.getStatus(),
      flightDocument.getPrice());
  }
}
