package io.bookingmicroservices.flight.aircrafts.models;

import buildingblocks.core.model.AggregateRoot;
import io.bookingmicroservices.flight.aircrafts.features.createaircraft.AircraftCreatedDomainEvent;
import io.bookingmicroservices.flight.aircrafts.valueobjects.AircraftId;
import io.bookingmicroservices.flight.aircrafts.valueobjects.ManufacturingYear;
import io.bookingmicroservices.flight.aircrafts.valueobjects.Model;
import io.bookingmicroservices.flight.aircrafts.valueobjects.Name;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Getter
@Setter(AccessLevel.PRIVATE)
public class Aircraft extends AggregateRoot<AircraftId> {
  Name name;
  Model model;
  ManufacturingYear manufacturingYear;
  boolean isDeleted;

  public Aircraft(AircraftId aircraftId, Name name, Model model, ManufacturingYear manufacturingYear, boolean isDeleted) {
    this.id = aircraftId;
    this.name = name;
    this.model = model;
    this.manufacturingYear = manufacturingYear;
    this.isDeleted = isDeleted;
  }

  public static Aircraft create(
    AircraftId id,
    Name name,
    Model model,
    ManufacturingYear manufacturingYear,
    boolean isDeleted
  ) {
    var aircraft = new Aircraft(id, name, model, manufacturingYear, isDeleted);

    aircraft.addDomainEvent(new AircraftCreatedDomainEvent(
      aircraft.id.value(),
      aircraft.name.value(),
      aircraft.model.value(),
      aircraft.manufacturingYear.value(),
      isDeleted
    ));

    return aircraft;
  }
}
