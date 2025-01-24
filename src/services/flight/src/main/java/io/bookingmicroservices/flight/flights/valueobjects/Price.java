package io.bookingmicroservices.flight.flights.valueobjects;

import buildingblocks.utils.validation.ValidationUtils;
import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;


@Embeddable
@EqualsAndHashCode
@NoArgsConstructor // Required by JPA
@Getter
public class Price {
  private BigDecimal price;

  public Price(BigDecimal value) {
    ValidationUtils.notBeNegativeOrNull(value);

    this.price = value;
  }
}

