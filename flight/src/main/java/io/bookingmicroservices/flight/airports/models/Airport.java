package io.bookingmicroservices.flight.airports.models;

import buildingblocks.core.model.AggregateRoot;
import io.bookingmicroservices.flight.airports.features.createairport.AirportCreatedDomainEvent;
import io.bookingmicroservices.flight.airports.valueobjects.Address;
import io.bookingmicroservices.flight.airports.valueobjects.AirportId;
import io.bookingmicroservices.flight.airports.valueobjects.Code;
import io.bookingmicroservices.flight.airports.valueobjects.Name;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Getter
@Setter(AccessLevel.PRIVATE)
public class Airport extends AggregateRoot<AirportId> {
  Name name;
  Code code;
  Address address;
  boolean isDeleted;


  public Airport(AirportId airportId, Name name, Code code, Address address, boolean isDeleted) {
    this.id = airportId;
    this.name = name;
    this.code = code;
    this.address = address;
    this.isDeleted = isDeleted;
  }

  public static Airport create(
    AirportId id,
    Name name,
    Code code,
    Address address,
    boolean isDeleted
  ) {
    var airport = new Airport(id, name, code, address, isDeleted);

    airport.addDomainEvent(new AirportCreatedDomainEvent(
      airport.id.value(),
      airport.name.value(),
      airport.code.value(),
      airport.address.value(),
      isDeleted
    ));

    return airport;
  }
}
