package io.bookingmicroservices.flight.flights.valueobjects;

import buildingblocks.utils.validation.ValidationUtils;
import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Embeddable
@EqualsAndHashCode
@NoArgsConstructor // Required by JPA
@Getter
public class FlightDate {
  private LocalDateTime flightDate;

  public FlightDate(LocalDateTime value) {
    ValidationUtils.validLocalDateTime(value);

    this.flightDate = value;
  }
}

