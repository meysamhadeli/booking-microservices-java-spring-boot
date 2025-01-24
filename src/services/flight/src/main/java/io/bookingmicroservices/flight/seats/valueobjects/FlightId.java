package io.bookingmicroservices.flight.seats.valueobjects;

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
public class FlightId {
  private UUID flightId;

  public FlightId(UUID value) {
    ValidationUtils.notBeNullOrEmpty(value);

    this.flightId = value;
  }
}


