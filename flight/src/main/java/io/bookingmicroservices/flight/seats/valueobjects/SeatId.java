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
public class SeatId {
  private UUID seatId;

  public SeatId(UUID value) {
    ValidationUtils.notBeNullOrEmpty(value);

    this.seatId = value;
  }
}


