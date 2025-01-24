package io.bookingmicroservices.flight.airports.valueobjects;

import buildingblocks.utils.validation.ValidationUtils;
import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Embeddable
@EqualsAndHashCode
@NoArgsConstructor // Required by JPA
@Getter
public class Address {
  private String address;

  public Address(String value) {
    ValidationUtils.notBeNullOrEmpty(value);

    this.address = value;
  }
}


