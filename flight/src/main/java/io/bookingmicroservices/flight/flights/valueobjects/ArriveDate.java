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
public class ArriveDate {
  private LocalDateTime arriveDate;

  public ArriveDate(LocalDateTime value) {
    ValidationUtils.validLocalDateTime(value);

    this.arriveDate = value;
  }
}

