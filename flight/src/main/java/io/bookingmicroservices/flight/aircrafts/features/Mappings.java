package io.bookingmicroservices.flight.aircrafts.features;

import com.github.f4b6a3.uuid.UuidCreator;
import io.bookingmicroservices.flight.aircrafts.dtos.AircraftDto;
import io.bookingmicroservices.flight.aircrafts.features.createaircraft.CreateAircraftCommand;
import io.bookingmicroservices.flight.aircrafts.features.createaircraft.CreateAircraftMongoCommand;
import io.bookingmicroservices.flight.aircrafts.features.createaircraft.CreateAircraftRequestDto;
import io.bookingmicroservices.flight.aircrafts.models.Aircraft;
import io.bookingmicroservices.flight.aircrafts.valueobjects.AircraftId;
import io.bookingmicroservices.flight.aircrafts.valueobjects.ManufacturingYear;
import io.bookingmicroservices.flight.aircrafts.valueobjects.Model;
import io.bookingmicroservices.flight.aircrafts.valueobjects.Name;
import io.bookingmicroservices.flight.data.jpa.entities.AircraftEntity;
import io.bookingmicroservices.flight.data.mongo.documents.AircraftDocument;

public final class Mappings {

  public static AircraftEntity toAircraftEntity(Aircraft aircraft) {
    return new AircraftEntity(
      aircraft.getId().value(),
      aircraft.getName().value(),
      aircraft.getModel().value(),
      aircraft.getManufacturingYear().value()
    );
  }

  public static Aircraft toAircraftAggregate(AircraftEntity aircraftEntity) {
    return new Aircraft(
      new AircraftId(aircraftEntity.getId()),
      new Name(aircraftEntity.getName()),
      new Model(aircraftEntity.getModel()),
      new ManufacturingYear(aircraftEntity.getManufacturingYear()),
      aircraftEntity.isDeleted()
    );
  }

  public static Aircraft toAircraftAggregate(CreateAircraftCommand createAircraftCommand) {
    return Aircraft.create(
     new AircraftId(createAircraftCommand.id()),
      new Name(createAircraftCommand.name()),
      new Model(createAircraftCommand.model()),
      new ManufacturingYear(createAircraftCommand.manufacturingYear()),
      false
    );
  }

  public static AircraftDto toAircraftDto(Aircraft aircraft) {
    return new AircraftDto(
      aircraft.getId().value(),
      aircraft.getName().value(),
      aircraft.getModel().value(),
      aircraft.getManufacturingYear().value());
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
      aircraftEntity.getName(),
      aircraftEntity.getModel(),
      aircraftEntity.getManufacturingYear(),
      aircraftEntity.isDeleted()
    );
  }
}
