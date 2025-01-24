package io.bookingmicroservices.flight.aircrafts.valueobjects;


import buildingblocks.utils.validation.ValidationUtils;
import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;


@Embeddable
@EqualsAndHashCode
@NoArgsConstructor // Required by JPA
@Getter
public class AircraftId {
  private UUID aircraftId;

  public AircraftId(UUID value) {
    ValidationUtils.notBeNullOrEmpty(value);

    this.aircraftId = value;
  }
}


