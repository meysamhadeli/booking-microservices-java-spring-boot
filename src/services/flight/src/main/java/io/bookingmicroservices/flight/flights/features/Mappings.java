package io.bookingmicroservices.flight.flights.features;

import buildingblocks.utils.protobuf.ProtobufUtils;
import com.github.f4b6a3.uuid.UuidCreator;
import io.bookingmicroservices.flight.aircrafts.valueobjects.AircraftId;
import io.bookingmicroservices.flight.airports.valueobjects.AirportId;
import io.bookingmicroservices.flight.data.jpa.entities.FlightEntity;
import io.bookingmicroservices.flight.data.mongo.documents.FlightDocument;
import io.bookingmicroservices.flight.flights.dtos.FlightDto;
import io.bookingmicroservices.flight.flights.enums.FlightStatus;
import io.bookingmicroservices.flight.flights.features.createflight.CreateFlightCommand;
import io.bookingmicroservices.flight.flights.features.createflight.CreateFlightMongoCommand;
import io.bookingmicroservices.flight.flights.features.createflight.CreateFlightRequestDto;
import io.bookingmicroservices.flight.flights.features.deleteflight.DeleteFlightMongoCommand;
import io.bookingmicroservices.flight.flights.features.updateflight.UpdateFlightCommand;
import io.bookingmicroservices.flight.flights.features.updateflight.UpdateFlightMongoCommand;
import io.bookingmicroservices.flight.flights.features.updateflight.UpdateFlightRequestDto;
import io.bookingmicroservices.flight.flights.models.Flight;
import io.bookingmicroservices.flight.flights.valueobjects.*;
import org.bson.types.ObjectId;

import java.util.UUID;

public final class Mappings {

  public static FlightEntity toFlightEntity(Flight flight) {
    return new FlightEntity(
      flight.getId().getFlightId(),
      flight.getFlightNumber(),
      flight.getAircraftId(),
      flight.getDepartureAirportId(),
      flight.getArriveAirportId(),
      flight.getDurationMinutes(),
      flight.getStatus(),
      flight.getPrice(),
      flight.getArriveDate(),
      flight.getDepartureDate(),
      flight.getFlightDate(),
      flight.getCreatedAt(),
      flight.getCreatedBy(),
      flight.getLastModified(),
      flight.getLastModifiedBy(),
      flight.getVersion(),
      flight.isDeleted()
    );
  }

  public static Flight toFlightAggregate(FlightEntity flightEntity) {
    return new Flight(
      new FlightId(flightEntity.getId()),
      flightEntity.getFlightNumber(),
      flightEntity.getAircraftId(),
      flightEntity.getArriveAirportId(),
      flightEntity.getDepartureAirportId(),
      flightEntity.getDurationMinutes(),
      flightEntity.getStatus(),
      flightEntity.getPrice(),
      flightEntity.getArriveDate(),
      flightEntity.getDepartureDate(),
      flightEntity.getFlightDate(),
      flightEntity.getCreatedAt(),
      flightEntity.getCreatedBy(),
      flightEntity.getLastModified(),
      flightEntity.getLastModifiedBy(),
      flightEntity.getVersion(),
      flightEntity.isDeleted()
    );
  }

  public static FlightEntity toFlightEntity(CreateFlightCommand createFlightCommand) {
    return new FlightEntity(
      createFlightCommand.id(),
      new FlightNumber(createFlightCommand.flightNumber()),
      new AircraftId(createFlightCommand.aircraftId()),
      new AirportId(createFlightCommand.departureAirportId()),
      new AirportId(createFlightCommand.arriveAirportId()),
      new DurationMinutes(createFlightCommand.durationMinutes()),
      createFlightCommand.status(),
      new Price(createFlightCommand.price()),
      new ArriveDate(createFlightCommand.arriveDate()),
      new DepartureDate(createFlightCommand.departureDate()),
      new FlightDate(createFlightCommand.flightDate())
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

  public static UpdateFlightCommand toUpdateFlightCommand(UUID id, UpdateFlightRequestDto updateFlightRequestDto) {
    return new UpdateFlightCommand(
      id,
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


  public static FlightDocument toFlightDocument(ObjectId id, UpdateFlightMongoCommand updateFlightMongoCommand) {
    return new FlightDocument(
      id,
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
      flightEntity.getFlightNumber().getFlightNumber(),
      flightEntity.getAircraftId().getAircraftId(),
      flightEntity.getDepartureAirportId().getAirportId(),
      flightEntity.getArriveAirportId().getAirportId(),
      flightEntity.getDurationMinutes().getDurationMinutes(),
      flightEntity.getStatus(),
      flightEntity.getPrice().getPrice(),
      flightEntity.getArriveDate().getArriveDate(),
      flightEntity.getDepartureDate().getDepartureDate(),
      flightEntity.getFlightDate().getFlightDate(),
      flightEntity.isDeleted()
    );
  }

  public static FlightDto toFlightDto(FlightEntity flightEntity) {
    return new FlightDto(
      flightEntity.getId(),
      flightEntity.getFlightNumber().getFlightNumber(),
      flightEntity.getAircraftId().getAircraftId(),
      flightEntity.getDepartureAirportId().getAirportId(),
      flightEntity.getDepartureDate().getDepartureDate(),
      flightEntity.getArriveDate().getArriveDate(),
      flightEntity.getArriveAirportId().getAirportId(),
      flightEntity.getDurationMinutes().getDurationMinutes(),
      flightEntity.getFlightDate().getFlightDate(),
      flightEntity.getStatus(),
      flightEntity.getPrice().getPrice());
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

  public static flight.Flight.FlightResponseDto toFlightResponseDtoGrpc(FlightDto flightDto) {
        return flight.Flight.FlightResponseDto.newBuilder()
      .setId(flightDto.id().toString())
          .setFlightNumber(flightDto.flightNumber())
          .setAircraftId(flightDto.aircraftId().toString())
          .setDepartureAirportId(flightDto.departureAirportId().toString())
          .setArriveAirportId(flightDto.arriveAirportId().toString())
          .setDepartureDate(ProtobufUtils.toProtobufTimestamp(flightDto.departureDate()))
          .setArriveDate(ProtobufUtils.toProtobufTimestamp(flightDto.arriveDate()))
          .setDurationMinutes(flightDto.durationMinutes().doubleValue())
          .setFlightDate(ProtobufUtils.toProtobufTimestamp(flightDto.flightDate()))
          .setStatus(toFlightStatusGrpc(flightDto.status()))
          .setPrice(flightDto.price().doubleValue())
      .build();
  }

  public static flight.Flight.FlightStatus toFlightStatusGrpc(FlightStatus flightStatus) {
    return switch (flightStatus) {
      case Flying -> flight.Flight.FlightStatus.FLIGHT_STATUS_FLYING;
      case Delay -> flight.Flight.FlightStatus.FLIGHT_STATUS_DELAY;
      case Completed -> flight.Flight.FlightStatus.FLIGHT_STATUS_COMPLETED;
      case Canceled -> flight.Flight.FlightStatus.FLIGHT_STATUS_CANCELED;
    };
  }
}
