package io.bookingmicroservices.flight.airports.features;

import com.github.f4b6a3.uuid.UuidCreator;
import io.bookingmicroservices.flight.airports.dtos.AirportDto;
import io.bookingmicroservices.flight.airports.features.createairport.CreateAirportCommand;
import io.bookingmicroservices.flight.airports.features.createairport.CreateAirportMongoCommand;
import io.bookingmicroservices.flight.airports.features.createairport.CreateAirportRequestDto;
import io.bookingmicroservices.flight.airports.models.Airport;
import io.bookingmicroservices.flight.data.jpa.entities.AirportEntity;
import io.bookingmicroservices.flight.data.mongo.documents.AirportDocument;

public final class Mappings {

  public static AirportEntity toAirportEntity(Airport airport) {
    return new AirportEntity(
      airport.getId().value(),
      airport.getName().value(),
      airport.getCode().value(),
      airport.getAddress().value(),
      airport.getCreatedAt(),
      airport.getCreatedBy(),
      airport.getLastModified(),
      airport.getLastModifiedBy(),
      airport.getVersion(),
      airport.isDeleted()
    );
  }


  public static AirportDto toAirportDto(AirportEntity airportEntity) {
    return new AirportDto(
      airportEntity.getId(),
      airportEntity.getName(),
      airportEntity.getCode(),
      airportEntity.getAddress());
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
