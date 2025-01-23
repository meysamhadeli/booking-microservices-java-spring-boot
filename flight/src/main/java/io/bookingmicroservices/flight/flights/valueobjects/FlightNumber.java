package io.bookingmicroservices.flight.flights.valueobjects;

import buildingblocks.utils.validation.ValidationUtils;
import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Embeddable
@EqualsAndHashCode
@NoArgsConstructor // Required by JPA
@Getter
public class FlightNumber {
  private String flightNumber;

  public FlightNumber(String value) {
    ValidationUtils.notBeNullOrEmpty(value);

    this.flightNumber = value;
  }
}
