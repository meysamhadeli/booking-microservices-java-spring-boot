package io.bookingmicroservices.flight.airports.valueobjects;

import buildingblocks.utils.validation.ValidationUtils;
import java.util.UUID;

public record AirportId(UUID value) {
    public AirportId {
      ValidationUtils.notBeNullOrEmpty(value);
    }
}
