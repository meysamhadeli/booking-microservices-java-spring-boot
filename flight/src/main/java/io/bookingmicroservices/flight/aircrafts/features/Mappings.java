package io.bookingmicroservices.flight.aircrafts.features;

import com.github.f4b6a3.uuid.UuidCreator;
import io.bookingmicroservices.flight.aircrafts.dtos.AircraftDto;
import io.bookingmicroservices.flight.aircrafts.features.createaircraft.CreateAircraftCommand;
import io.bookingmicroservices.flight.aircrafts.features.createaircraft.CreateAircraftMongoCommand;
import io.bookingmicroservices.flight.aircrafts.features.createaircraft.CreateAircraftRequestDto;
import io.bookingmicroservices.flight.aircrafts.models.Aircraft;
import io.bookingmicroservices.flight.data.jpa.entities.AircraftEntity;
import io.bookingmicroservices.flight.data.mongo.documents.AircraftDocument;

public final class Mappings {

  public static AircraftEntity toAircraftEntity(Aircraft aircraft) {
    return new AircraftEntity(
      aircraft.getId().getAircraftId(),
      aircraft.getName(),
      aircraft.getModel(),
      aircraft.getManufacturingYear(),
      aircraft.getCreatedAt(),
      aircraft.getCreatedBy(),
      aircraft.getLastModified(),
      aircraft.getLastModifiedBy(),
      aircraft.getVersion(),
      aircraft.isDeleted()
    );
  }


  public static AircraftDto toAircraftDto(AircraftEntity aircraftEntity) {
    return new AircraftDto(
      aircraftEntity.getId(),
      aircraftEntity.getName().getName(),
      aircraftEntity.getModel().getModel(),
      aircraftEntity.getManufacturingYear().getManufacturingYear());
  }

  public static CreateAircraftCommand toCreateAircraftCommand(CreateAircraftRequestDto createAircraftRequestDto) {
    return new CreateAircraftCommand(
      UuidCreator.getTimeOrderedEpoch(),
      createAircraftRequestDto.name(),
      createAircraftRequestDto.model(),
      createAircraftRequestDto.manufacturingYear()
    );
  }

  public static AircraftDocument toAircraftDocument(CreateAircraftMongoCommand createAircraftMongoCommand) {
    return new AircraftDocument(
      createAircraftMongoCommand.id(),
      createAircraftMongoCommand.name(),
      createAircraftMongoCommand.model(),
      createAircraftMongoCommand.manufacturingYear(),
      createAircraftMongoCommand.isDeleted()
    );
  }

  public static AircraftDocument toAircraftDocument(AircraftEntity aircraftEntity) {
    return new AircraftDocument(
      aircraftEntity.getId(),
      aircraftEntity.getName().getName(),
      aircraftEntity.getModel().getModel(),
      aircraftEntity.getManufacturingYear().getManufacturingYear(),
      aircraftEntity.isDeleted()
    );
  }
}
