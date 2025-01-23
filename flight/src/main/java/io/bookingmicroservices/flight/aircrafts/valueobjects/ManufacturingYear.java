package io.bookingmicroservices.flight.aircrafts.valueobjects;

import buildingblocks.utils.validation.ValidationUtils;
import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Embeddable
@EqualsAndHashCode
@NoArgsConstructor // Required by JPA
@Getter
public class ManufacturingYear {
  private int manufacturingYear;

  public ManufacturingYear(int value) {
    ValidationUtils.notBeNegativeOrNull(value);

    this.manufacturingYear = value;
  }
}



