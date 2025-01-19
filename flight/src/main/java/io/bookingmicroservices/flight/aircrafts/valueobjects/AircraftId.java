package io.bookingmicroservices.flight.aircrafts.valueobjects;


import buildingblocks.utils.validation.ValidationUtils;
import java.util.UUID;

public record AircraftId(UUID value) {
    public AircraftId {
      ValidationUtils.notBeNullOrEmpty(value);
    }
}
