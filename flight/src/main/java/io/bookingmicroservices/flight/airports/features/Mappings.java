package io.bookingmicroservices.flight.airports.features;

import com.github.f4b6a3.uuid.UuidCreator;
import io.bookingmicroservices.flight.airports.dtos.AirportDto;
import io.bookingmicroservices.flight.airports.features.createairport.CreateAirportCommand;
import io.bookingmicroservices.flight.airports.features.createairport.CreateAirportMongoCommand;
import io.bookingmicroservices.flight.airports.features.createairport.CreateAirportRequestDto;
import io.bookingmicroservices.flight.airports.models.Airport;
import io.bookingmicroservices.flight.airports.valueobjects.Address;
import io.bookingmicroservices.flight.airports.valueobjects.AirportId;
import io.bookingmicroservices.flight.airports.valueobjects.Code;
import io.bookingmicroservices.flight.airports.valueobjects.Name;
import io.bookingmicroservices.flight.data.jpa.entities.AirportEntity;
import io.bookingmicroservices.flight.data.mongo.entities.AirportDocument;

public final class Mappings {

  public static AirportEntity toAirportEntity(Airport airport) {
    return new AirportEntity(
      airport.getId().value(),
      airport.getName().value(),
      airport.getCode().value(),
      airport.getAddress().value()
    );
  }

  public static Airport toAirportAggregate(AirportEntity airportEntity) {
    return new Airport(
      new AirportId(airportEntity.getId()),
      new Name(airportEntity.getName()),
      new Code(airportEntity.getCode()),
      new Address(airportEntity.getAddress()),
      airportEntity.isDeleted()
    );
  }

  public static Airport toAirportAggregate(CreateAirportCommand createAirportCommand) {
    return Airport.create(
      new AirportId(createAirportCommand.id()),
      new Name(createAirportCommand.name()),
      new Code(createAirportCommand.code()),
      new Address(createAirportCommand.address()),
      false
    );
  }

  public static AirportDto toAirportDto(Airport airport) {
    return new AirportDto(
      airport.getId().value(),
      airport.getName().value(),
      airport.getCode().value(),
      airport.getAddress().value());
  }

  public static CreateAirportCommand toCreateAirportCommand(CreateAirportRequestDto createAirportRequestDto) {
    return new CreateAirportCommand(
      UuidCreator.getTimeOrderedEpoch(),
      createAirportRequestDto.name(),
      createAirportRequestDto.code(),
      createAirportRequestDto.address()
    );
  }

  public static AirportDocument toAirportDocument(CreateAirportMongoCommand createAirportMongoCommand) {
    return new AirportDocument(
      createAirportMongoCommand.id(),
      createAirportMongoCommand.name(),
      createAirportMongoCommand.code(),
      createAirportMongoCommand.address(),
      createAirportMongoCommand.isDeleted()
    );
  }

  public static AirportDocument toAirportDocument(AirportEntity airportEntity) {
    return new AirportDocument(
      airportEntity.getId(),
      airportEntity.getName(),
      airportEntity.getCode(),
      airportEntity.getAddress(),
      airportEntity.isDeleted()
    );
  }
}
